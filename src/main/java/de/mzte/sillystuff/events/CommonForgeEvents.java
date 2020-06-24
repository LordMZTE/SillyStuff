package de.mzte.sillystuff.events;

import de.mzte.sillystuff.util.IterationHelper;
import de.mzte.sillystuff.util.RegistryHelper;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {
    @SubscribeEvent
    public static void entityJoin(final EntityJoinWorldEvent e) {
        if(e.getEntity() instanceof VillagerEntity) {
            VillagerEntity villager = (VillagerEntity)e.getEntity();
            if(!IterationHelper.anyMatch(g -> g.getGoal() instanceof TemptGoal, villager.goalSelector.goals))
                villager.goalSelector.addGoal(
                        2,
                        new TemptGoal(
                                villager,
                                0.6,
                                Ingredient.fromItems(RegistryHelper.grabModItem("emerald_on_a_stick")),
                                false
                        )
                );
        }
    }
}
