package de.mzte.sillystuff.items;

import de.mzte.sillystuff.Config;
import de.mzte.sillystuff.util.PositionHelper;
import de.mzte.sillystuff.util.SerializationHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RecallPearl extends Item {

    public RecallPearl(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(playerIn.isCrouching() && (Config.CHANGE_RECALL_PEARL_LOCATION.get() || !isActive(stack))) {
            if(!worldIn.isRemote()) {
                setDim(stack, (ServerWorld) worldIn);
                setPos(stack, PositionHelper.blockPosFromVec3d(playerIn.getPositionVec()));
            }else
                playerIn.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1, 1);
        }else {
            if(isActive(stack)) {
                if(!worldIn.isRemote) {
                    teleportToDimension((ServerPlayerEntity) playerIn, getDim(stack, worldIn.getServer()), PositionHelper.vec3dFromBlockPos(getPos(stack)).add(0.5d, 0, 0.5d));
                    if(Config.CONSUME_RECALL_PEARL.get() && !playerIn.abilities.isCreativeMode)
                        stack.shrink(1);
                }
            }else {
                if(worldIn.isRemote)
                    playerIn.sendMessage((new TranslationTextComponent("chat.sillystuff.recall_pearl.nolocation").mergeStyle(TextFormatting.RED)), Util.DUMMY_UUID);

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

    private ServerWorld getDim(ItemStack i, MinecraftServer server) {
        if(!i.hasTag())
            return null;

        CompoundNBT tags = i.getTag();

        if(!tags.contains("dim"))
            return null;

        return SerializationHelper.deserializeDimension(tags.getString("dim"), server);
    }

    private void setDim(ItemStack i, ServerWorld w) {
        CompoundNBT nbt = i.getTag();
        if(!i.hasTag())
            nbt = new CompoundNBT();

        nbt.remove("dim");
        nbt.putString("dim", SerializationHelper.serializeDimension(w));
        i.setTag(nbt);
    }

    private boolean isActive(ItemStack i) {
        CompoundNBT nbt = i.getTag();
        return nbt != null && nbt.contains("pos") && nbt.contains("dim");
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isActive(stack);
    }

    private void teleportToDimension(ServerPlayerEntity player, ServerWorld world, Vector3d pos) {
        player.teleport(
                world,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                player.getYaw(1f),
                player.getPitch(1f)
        );
        player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
    }
}
