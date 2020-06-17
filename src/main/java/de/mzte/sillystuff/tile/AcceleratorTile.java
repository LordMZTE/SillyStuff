package de.mzte.sillystuff.tile;

import de.mzte.sillystuff.Config;
import de.mzte.sillystuff.util.CompareHelper;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class AcceleratorTile extends TileEntity implements ITickableTileEntity {
    public int speedLevel = 0;

    public AcceleratorTile() {
        super(ForgeRegistries.TILE_ENTITIES.getValue(new ResourceLocation(MODID, "accelerator")));
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            TileEntity toTick = world.getTileEntity(pos.offset(world.getBlockState(pos).get(BlockStateProperties.FACING)));
            if(!world.isBlockPowered(this.pos) && toTick != null) {
                speedLevel = Math.min(this.world.loadedTileEntityList.stream()
                        .filter(t -> t instanceof BeaconTileEntity)
                        .map(t -> (BeaconTileEntity)t)
                        .filter(this::isBeaconValid)
                        .mapToInt(this::getSpeedLevel)
                        .sum(), Config.ACCELERATOR_MAX_SPEED.get());

                if(speedLevel > 0 && (toTick instanceof ITickableTileEntity) && !CompareHelper.objectExtends(toTick, this)) {
                    setActiveState(true);
                    for(int i = 0; i < Config.ACCELERATOR_ACCELERATION.get() * speedLevel; i++)
                        ((ITickableTileEntity)toTick).tick();
                }else
                    setActiveState(false);
            }else
                setActiveState(false);
        }
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
}
