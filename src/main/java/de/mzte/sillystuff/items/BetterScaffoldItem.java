package de.mzte.sillystuff.items;

import de.mzte.sillystuff.util.CompareHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockState blockstate = world.getBlockState(blockpos);
        Block block = this.getBlock();
        Direction direction;

        //Player is looking down-ish
        if(context.getPlayer().getPitch(1f) - 20 >= 60)
            direction = Direction.DOWN;
        else if(context.getFace() == Direction.UP)
            direction = context.getPlacementHorizontalFacing();
        else
            direction = Direction.UP;

        int i = 0;
        BlockPos.Mutable blockClicked = blockpos.func_239590_i_().move(context.getFace().getOpposite());
        BlockPos.Mutable blockpos$mutable = blockpos.func_239590_i_().move(context.getFace().getOpposite()).move(direction);
        if(CompareHelper.objectExtends(world.getBlockState(blockClicked).getBlock(), block) && context.getPlayer().isCrouching()) {
            while(i < 100) {
                blockstate = world.getBlockState(blockpos$mutable);
                if(!CompareHelper.objectExtends(blockstate.getBlock(), block)) {
                    if(blockstate.isReplaceable(context)) {
                        return BlockItemUseContext.func_221536_a(context, blockpos$mutable, direction);
                    }
                    break;
                }
                if( blockpos$mutable.getY() > world.getHeight() && player instanceof ServerPlayerEntity) {
                    SChatPacket schatpacket = new SChatPacket((new TranslationTextComponent("build.tooHigh", world.getHeight())).func_240701_a_(TextFormatting.RED), ChatType.GAME_INFO, Util.field_240973_b_);
                    ((ServerPlayerEntity)player).connection.sendPacket(schatpacket);
                    break;
                }

                blockpos$mutable.move(direction);
                ++i;
            }
        }else {
            return context;
        }
        return null;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 32;
    }
}
