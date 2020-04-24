package de.mzte.sillystuff.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.ITEM_GROUP;
import static de.mzte.sillystuff.SillyStuff.MODID;
import static de.mzte.sillystuff.items.ModItems.ITEMS;

public class ModBlocks {
    private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);

        registerBlockWithBasicItem("better_scaffold", new BetterScaffold(Block.Properties.create(Material.BAMBOO)
            .notSolid()
            .sound(SoundType.SCAFFOLDING)
            .harvestLevel(0)
            .hardnessAndResistance(0)
            .lightValue(15)));
    }



    private static void registerBlock(String name, Block block) {
        BLOCKS.register(name, () -> block);
    }

    private static void registerBlockWithBasicItem(String name, Block block) {
        registerBlock(name, block);
        ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(ITEM_GROUP)));
    }

}
