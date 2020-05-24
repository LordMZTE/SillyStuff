package de.mzte.sillystuff.items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.ITEM_GROUP;
import static de.mzte.sillystuff.SillyStuff.MODID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);

        ITEMS.register("animal_grower", () -> new AnimalGrower(new Item.Properties().group(ITEM_GROUP)));
        ITEMS.register("better_scaffold", () -> new BetterScaffoldItem(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "better_scaffold")), new Item.Properties()
                .group(ITEM_GROUP)));
        ITEMS.register("boiled_sweet_berries", () -> new Item((new Item.Properties())
                .group(ITEM_GROUP)
                .food(new Food.Builder()
                        .saturation(0.4F)
                        .hunger(5).build())));
    }

}
