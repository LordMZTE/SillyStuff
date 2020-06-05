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
            BlockPos.getAllInBox(pos.add(1, 1, 1), pos.add(-1, -1, -1))
                    .filter(p -> worldIn.getBlockState(p).getBlock() instanceof BetterScaffold)
                    .forEach(p -> {
                        worldIn.setBlockState(p, worldIn.getBlockState(p).with(BREAKS, true));
                        worldIn.getPendingBlockTicks().scheduleTick(p, worldIn.getBlockState(p).getBlock(), 4);
                    });
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
