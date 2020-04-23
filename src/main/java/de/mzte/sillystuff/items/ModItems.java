package de.mzte.sillystuff.items;

import net.minecraft.item.Item;

import static de.mzte.sillystuff.SillyStuff.ITEMS;
import static de.mzte.sillystuff.SillyStuff.ITEM_GROUP;

public class ModItems {

    public static void register() {
        ITEMS.register("animal_grower", () -> new AnimalGrower(new Item.Properties().group(ITEM_GROUP)));
    }
}
