package de.mzte.sillystuff.items;

import de.mzte.sillystuff.util.CompareHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BetterScaffoldItem extends BlockItem {
    public BetterScaffoldItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }
    @Override
    @Nullable
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        World world = context.getWorld();
        BlockState blockstate = world.getBlockState(blockpos);
        Block block = this.getBlock();
        Direction direction;
        if(context.getFace() == Direction.UP) {
            direction = context.getPlacementHorizontalFacing();
        }else {
            return context;
        }

        int i = 0;
        BlockPos.Mutable blockClicked = (new BlockPos.Mutable(blockpos)).move(context.getFace().getOpposite());
        BlockPos.Mutable blockpos$mutable = (new BlockPos.Mutable(blockpos)).move(context.getFace().getOpposite()).move(direction);
        if(CompareHelper.objectExtends(world.getBlockState(blockClicked).getBlock(), block) && context.getPlayer().isCrouching()) {
            while (i < 100) {
                blockstate = world.getBlockState(blockpos$mutable);
                if (!CompareHelper.objectExtends(blockstate.getBlock(), block)) {
                    if (blockstate.isReplaceable(context)) {
                        return BlockItemUseContext.func_221536_a(context, blockpos$mutable, direction);
                    }
                    break;
                }

                blockpos$mutable.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    ++i;
                }
            }
            }else{
                return context;
            }
        return null;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 32;
    }
}
