package de.mzte.sillystuff.items;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.stream.Stream;

public class BigTool extends ToolItem {
    private final int radius, depth;

    public BigTool(float attackDamageIn, float attackSpeedIn, IItemTier tier, Properties builder, int radius, int depth) {
        super(attackDamageIn, attackSpeedIn, tier, ImmutableSet.of(), builder);
        this.radius = radius;
        this.depth = depth;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entityLiving;
            RayTraceResult ray = Item.rayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);
            if(ray.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockRay = (BlockRayTraceResult)ray;
                Direction facing = blockRay.getFace();
                getArea(pos, facing)
                        .forEach(b -> {
                            if(worldIn instanceof ServerWorld &&
                                    worldIn.getTileEntity(b) == null &&
                                    entityLiving instanceof ServerPlayerEntity &&
                                    !worldIn.isAirBlock(b)) {
                                BlockState tempState = worldIn.getBlockState(b);
                                Block block = tempState.getBlock();
                                if(!this.getToolTypes(stack).contains(tempState.getHarvestTool()) || player.isCrouching() || block.getBlockHardness(tempState, worldIn, b) < 0)
                                    return;
                                block.onPlayerDestroy(worldIn, b, tempState);
                                block.harvestBlock(worldIn, player, b, tempState, null, stack);
                                worldIn.destroyBlock(b, false);
                            }
                        });
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    private Stream<BlockPos> getArea(BlockPos block, Direction face) {
        BlockPos bottomLeft, topRight;

        if(face == Direction.DOWN || face == Direction.UP) {
            bottomLeft = block
                    .south(this.radius)
                    .west(this.radius)
                    .offset(face, depth);

            topRight = block
                    .north(this.radius)
                    .east(this.radius);
        }else {
            //Move Down By Radius
            bottomLeft = block.down(this.radius)
                    //Move In By Depth
                    .offset(face, this.depth)
                    //Rotate CCW and move by radius
                    .offset(face.rotateYCCW(), this.radius);

            //Move Up by radius
            topRight = block.up(this.radius)
                    //Rotate Clockwise and expand by radius
                    .offset(face.rotateY(), radius);
        }

        return BlockPos.getAllInBox(bottomLeft, topRight);
    }
}
