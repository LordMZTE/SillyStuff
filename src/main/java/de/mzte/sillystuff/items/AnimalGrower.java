package de.mzte.sillystuff.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class AnimalGrower extends Item {
    private static final Random rand = new Random();

    public AnimalGrower(Properties properties) {
        super(properties);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if(target instanceof AnimalEntity && ((AnimalEntity) target).getGrowingAge() < 0) {
            if (!target.world.isRemote) {
                if(!playerIn.abilities.isCreativeMode)
                    playerIn.getHeldItem(hand).shrink(1);
                ((AnimalEntity) target).setGrowingAge(0);
                for (int i = 0; i < 10; ++i) {
                    ((ServerWorld) target.getEntityWorld()).spawnParticle(ParticleTypes.HAPPY_VILLAGER, target.getPosXRandom(1.0D), target.getPosYRandom() + 1.0D, target.getPosZRandom(1.0D), 2, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, 5D);
                }
            }
            return true;
        } else if(target instanceof CreeperEntity && !((CreeperEntity) target).hasIgnited()) {
            if(!target.world.isRemote) {
                ((CreeperEntity) target).ignite();
                playerIn.getHeldItem(hand).shrink(1);
            }
            return true;
        }
        return false;
    }
}
