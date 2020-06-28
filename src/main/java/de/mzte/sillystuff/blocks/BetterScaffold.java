package de.mzte.sillystuff.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BetterScaffold extends Block {
    public static final BooleanProperty BREAKS = BooleanProperty.create("breaks");
    public static final VoxelShape SHAPE = VoxelShapes.create(0.1, 0.0, 0.1, 0.9, 1.0, 0.9);


    public BetterScaffold(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BREAKS, false));

    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if(player.isSneaking()) {
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

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        applyLadderLogic(entityIn);
    }

    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    //Credit for this method goes to BluSunrize
    public static void applyLadderLogic(Entity entityIn) {
        if(entityIn instanceof LivingEntity && !((LivingEntity)entityIn).isOnLadder()) {
            Vector3d motion = entityIn.getMotion();
            float maxMotion = 0.15F;
            motion = new Vector3d(
                    MathHelper.clamp(motion.x, -maxMotion, maxMotion),
                    Math.max(motion.y, -maxMotion),
                    MathHelper.clamp(motion.z, -maxMotion, maxMotion)
            );

            entityIn.fallDistance = 0.0F;

            if(motion.y < 0 && entityIn instanceof PlayerEntity && entityIn.isCrouching()) {
                motion = new Vector3d(motion.x, 0D, motion.z);
                entityIn.setPosition(entityIn.getPosX(), entityIn.prevPosY, entityIn.getPosZ());
            }
            else if(entityIn.collidedHorizontally)
                motion = new Vector3d(motion.x, 0.4, motion.z);
            entityIn.setMotion(motion);
        }
    }
}
