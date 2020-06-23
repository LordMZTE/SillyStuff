package de.mzte.sillystuff.items;

import de.mzte.sillystuff.Config;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class RecallPearl extends Item {

    public RecallPearl(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CompoundNBT nbt = stack.getTag();
        if(playerIn.isCrouching() && (Config.CHANGE_RECALL_PEARL_LOCATION.get() || !isActive(stack))) {
            setDim(stack, playerIn.dimension);
            setPos(stack, playerIn.getPosition());
            playerIn.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1, 1);
        }else {
            if(isActive(stack)) {
                if(!worldIn.isRemote) {
                    teleportToDimension(playerIn, getDim(stack), new Vec3d(getPos(stack)).add(0.5d, 0, 0.5d));
                    playerIn.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1, 1);
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

    private void teleportToDimension(PlayerEntity player, DimensionType dimension, Vec3d pos) {
        player.changeDimension(dimension, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
                return entity;
            }
        });
    }
}
