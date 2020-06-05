package de.mzte.sillystuff.items;

import de.mzte.sillystuff.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class RecallPearl extends Item {

    public RecallPearl(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CompoundNBT nbt = stack.getTag();
        if(playerIn.isCrouching() && (Config.CHANGE_RECALL_PEARL_LOCATION.get() || !isActive(stack))) {
            setPos(stack, playerIn.getPosition());
            setDim(stack, playerIn.dimension);
            playerIn.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1, 1);
        }else {
            if(isActive(stack)) {
                if(!worldIn.isRemote) {
                    BlockPos pos = getPos(stack);
                    playerIn.changeDimension(getDim(stack));
                    playerIn.setPositionAndUpdate(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
                    if(Config.CONSUME_RECALL_PEARL.get() && !playerIn.abilities.isCreativeMode)
                        stack.shrink(1);

                }
            }else {
                if(worldIn.isRemote)
                    playerIn.sendMessage((new TranslationTextComponent("chat.sillystuff.recall_pearl.nolocation").applyTextStyle(TextFormatting.RED)));

                return ActionResult.resultFail(stack);
            }
        }
        return ActionResult.resultSuccess(stack);
    }

    private BlockPos getPos(ItemStack i) {
        if(!i.hasTag())
            return null;

        CompoundNBT tags = i.getTag();

        if(tags.contains("pos"))
            return NBTUtil.readBlockPos((CompoundNBT) tags.get("pos"));

        return null;
    }

    private void setPos(ItemStack i, BlockPos p) {
        CompoundNBT nbt = i.getTag();
        if(!i.hasTag())
            nbt = new CompoundNBT();

        nbt.remove("pos");
        nbt.put("pos", NBTUtil.writeBlockPos(p));
        i.setTag(nbt);
    }

    private DimensionType getDim(ItemStack i) {
        if(!i.hasTag())
            return null;

        return DimensionType.byName(new ResourceLocation(i.getTag().getString("dim")));
    }

    private void setDim(ItemStack i, DimensionType d) {
        CompoundNBT nbt = i.getTag();
        if(!i.hasTag())
            nbt = new CompoundNBT();

        nbt.remove("dim");
        nbt.putString("dim", d.getRegistryName().toString());
        i.setTag(nbt);
    }

    private boolean isActive(ItemStack i) {
        if(!i.hasTag())
            return false;

        CompoundNBT nbt = i.getTag();
        return nbt != null && nbt.contains("pos") && nbt.contains("dim");
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isActive(stack);
    }
}
