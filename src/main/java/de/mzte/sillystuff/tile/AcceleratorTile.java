package de.mzte.sillystuff.tile;

import de.mzte.sillystuff.Config;
import de.mzte.sillystuff.util.CompareHelper;
import de.mzte.sillystuff.util.RegistryHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class AcceleratorTile extends TileEntity implements ITickableTileEntity {
    public int speedLevel = 0;
    public int growTicks = 0;
    public int acceleration = 0;

    public AcceleratorTile() {
        super(RegistryHelper.grabModTileEntity("accelerator"));
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            BlockPos toTickPos = pos.offset(world.getBlockState(pos).get(BlockStateProperties.FACING));
            BlockState toTick = world.getBlockState(toTickPos);
            if(!world.isBlockPowered(this.pos) && toTick != null) {
                speedLevel = Math.min(this.world.loadedTileEntityList.stream()
                        .filter(t -> t instanceof BeaconTileEntity)
                        .map(t -> (BeaconTileEntity)t)
                        .filter(this::isBeaconValid)
                        .mapToInt(this::getSpeedLevel)
                        .sum(), Config.ACCELERATOR_MAX_SPEED.get());

                acceleration = speedLevel * Config.ACCELERATOR_ACCELERATION.get();
                if(speedLevel > 0 && !(world.getTileEntity(toTickPos) instanceof AcceleratorTile)) {
                    if(world.getTileEntity(toTickPos) instanceof ITickableTileEntity) {
                        setActiveState(true);
                        for(int i = 0; i < acceleration; i++)
                            ((ITickableTileEntity)world.getTileEntity(toTickPos)).tick();
                    }else if(toTick.getBlock() instanceof IGrowable) {
                        IGrowable iGrowable = (IGrowable)toTick.getBlock();
                        setActiveState(true);
                        growTicks += acceleration;
                        if(iGrowable.canGrow(world, toTickPos, toTick, false) && growTicks >= Config.ACCELERATOR_CROP_TICKS.get()) {
                            iGrowable.grow((ServerWorld)world, world.rand, toTickPos, toTick);
                            spawnFertilizerParticles(world.rand, (ServerWorld)world, toTickPos);
                            growTicks = 0;
                        }
                        this.markDirty();
                    }else
                        setActiveState(false);
                }else
                    setActiveState(false);
            }else
                setActiveState(false);
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        this.growTicks = compound.getInt("growTicks");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("growTicks", growTicks);
        return compound;
    }

    private boolean isBeaconValid(BeaconTileEntity beacon) {
        return CompareHelper.isInCubeRange(
                beacon.getPos(),
                this.pos,
                Config.ACCELERATOR_BEACON_RANGE.get()) &&
                beacon.primaryEffect == Effects.SPEED &&
                beacon.levels > 0 && !beacon.beamSegments.isEmpty();
    }

    private void setActiveState(boolean state) {
        world.setBlockState(this.pos, this.world.getBlockState(this.pos)
                .with(BlockStateProperties.POWERED, state));
    }

    private int getSpeedLevel(BeaconTileEntity beacon) {
        if(beacon.secondaryEffect == Effects.SPEED && beacon.primaryEffect == Effects.SPEED)
            return speedLevel = 2;
        else if(beacon.primaryEffect == Effects.SPEED)
            return speedLevel = 1;
        else
            return speedLevel = 0;
    }

    private static void spawnFertilizerParticles(Random random, ServerWorld world, BlockPos pos) {
        for(int i = 0; i < 10; ++i) {
            world.spawnParticle(
                    ParticleTypes.HAPPY_VILLAGER,
                    (float)pos.getX() + random.nextFloat(),
                    (float)pos.getY() + random.nextFloat(),
                    (float)pos.getZ() + random.nextFloat(),
                    2,
                    random.nextGaussian() * 0.02D,
                    random.nextGaussian() * 0.02D,
                    random.nextGaussian() * 0.02D,
                    random.nextGaussian() * 0.02D
            );
        }
    }
}
