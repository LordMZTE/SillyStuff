package de.mzte.sillystuff.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Datagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent e) {
        DataGenerator gen = e.getGenerator();
        if(e.includeClient()) {
            gen.addProvider(new ItemModels(gen, e.getExistingFileHelper()));
            gen.addProvider(new BlockStates(gen, e.getExistingFileHelper()));
        }
        if(e.includeServer()) {
            gen.addProvider(new Recipes(gen));
            gen.addProvider(new LootTables(gen));
        }
    }
}
