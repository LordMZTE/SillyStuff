package de.mzte.sillystuff.items;

import net.minecraft.block.material.Material;
import net.minecraft.item.Food;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.ITEM_GROUP;
import static de.mzte.sillystuff.SillyStuff.MODID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);

        //region Big Tools
        registerBigTools("wooden", 1, -2.8F, ItemTier.WOOD);
        registerBigTools("golden", 1, -2.8F, ItemTier.GOLD);
        registerBigTools("stone", 1, -2.8F, ItemTier.STONE);
        registerBigTools("iron", 1, -2.8F, ItemTier.IRON);
        registerBigTools("diamond", 1, -2.8F, ItemTier.DIAMOND);
        //endregion

        //region Other Items
        ITEMS.register("animal_grower", () -> new AnimalGrower(new Item.Properties()
                .group(ITEM_GROUP)));
        ITEMS.register("better_scaffold", () -> new BetterScaffoldItem(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "better_scaffold")), new Item.Properties()
                .group(ITEM_GROUP)));
        ITEMS.register("illuminated_better_scaffold", () -> new BetterScaffoldItem(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "illuminated_better_scaffold")), new Item.Properties()
                .group(ITEM_GROUP)));
        ITEMS.register("boiled_sweet_berries", () -> new Item((new Item.Properties())
                .group(ITEM_GROUP)
                .food(new Food.Builder()
                        .saturation(0.4F)
                        .hunger(5).build())));
        ITEMS.register("recall_pearl", () -> new RecallPearl(new Item.Properties()
                .maxStackSize(1)
                .group(ITEM_GROUP)));
        //endregion
    }

    private static void registerBigTools(String name,
                                         int damage,
                                         float attackSpeed,
                                         IItemTier tier) {
        int miningLevel = tier.getHarvestLevel();
        //HAMMER
        ITEMS.register(name + "_hammer", () -> new BigTool(damage,
                attackSpeed,
                tier,
                new Item.Properties()
                        .group(ITEM_GROUP)
                        .addToolType(ToolType.PICKAXE, miningLevel),
                1,
                0));
        //EXCAVATOR
        ITEMS.register(name + "_excavator", () -> new BigTool(damage,
                attackSpeed,
                tier,
                new Item.Properties()
                        .group(ITEM_GROUP)
                        .addToolType(ToolType.SHOVEL, miningLevel),
                1,
                0));
        //GREAT AXE
        ITEMS.register(name + "_great_axe", () -> new BigTool(damage,
                attackSpeed,
                tier,
                new Item.Properties()
                        .group(ITEM_GROUP)
                        .addToolType(ToolType.AXE, miningLevel),
                1,
                2,
                Material.LEAVES,
                Material.BAMBOO,
                Material.BAMBOO_SAPLING));
    }

}
