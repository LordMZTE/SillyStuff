package de.mzte.sillystuff.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
public class BetterScaffold extends Block {
    public static final BooleanProperty BREAKS = BooleanProperty.create("breaks");

    public BetterScaffold(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BREAKS, false));

    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if(player.isCrouching()) {
            world.setBlockState(pos, world.getBlockState(pos).with(BREAKS, true));
            world.getPendingBlockTicks().scheduleTick(pos, this, 1);
            return false;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BREAKS);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if(state.get(BREAKS)) {
            if(worldIn.getBlockState(pos.up()).getBlock() == this) {
                worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(BREAKS, true));
                worldIn.getPendingBlockTicks().scheduleTick(pos.up(), this, 4);
            }if(worldIn.getBlockState(pos.down()).getBlock() == this) {
                worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(BREAKS, true));
                worldIn.getPendingBlockTicks().scheduleTick(pos.down(), this, 4);
            }if(worldIn.getBlockState(pos.north()).getBlock() == this) {
                worldIn.setBlockState(pos.north(), worldIn.getBlockState(pos.north()).with(BREAKS, true));
                worldIn.getPendingBlockTicks().scheduleTick(pos.north(), this, 4);
            }if(worldIn.getBlockState(pos.east()).getBlock() == this) {
                worldIn.setBlockState(pos.east(), worldIn.getBlockState(pos.east()).with(BREAKS, true));
                worldIn.getPendingBlockTicks().scheduleTick(pos.east(), this, 4);
            }if(worldIn.getBlockState(pos.west()).getBlock() == this) {
                worldIn.setBlockState(pos.west(), worldIn.getBlockState(pos.west()).with(BREAKS, true));
                worldIn.getPendingBlockTicks().scheduleTick(pos.west(), this, 4);
            }if(worldIn.getBlockState(pos.south()).getBlock() == this) {
                worldIn.setBlockState(pos.south(), worldIn.getBlockState(pos.south()).with(BREAKS, true));
                worldIn.getPendingBlockTicks().scheduleTick(pos.south(), this, 4);
            }
            worldIn.destroyBlock(pos, true);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }
}
