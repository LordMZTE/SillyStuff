package de.mzte.sillystuff.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class RegistryHelper {
    /**
     * will grab a mod item from the item registry
     * @param id the registry name of the item
     * @return the item
     */
    public static Item grabModItem(String id) {
        return grabItem(MODID, id);
    }

    /**
     * will grab an item from the registry
     * @param modId the ID of the mod of the item
     * @param id the registry name of the item
     * @return the item
     */
    public static Item grabItem(String modId, String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, id));
    }

    /**
     * will grab a mod block from the registry
     * @param id the registry name of the block
     * @return the block
     */
    public static Block grabModBlock(String id) {
        return grabBlock(MODID, id);
    }

    /**
     * will grab a block from the registry
     * @param modId the ID of the mod of the block
     * @param id the registry name of the block
     * @return the block
     */
    public static Block grabBlock(String modId, String id) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modId, id));
    }

    /**
     * will grab a mod tile entity from the registry
     * @param id the registry name of the tile entity
     * @return the tile entity
     */
    public static TileEntityType<?> grabModTileEntity(String id) {
        return grabTileEntity(MODID, id);
    }

    /**
     * will grab a tile entity from the registry
     * @param modId the ID of the mod of the tile entity
     * @param id the registry name of the tile entity
     * @return the tile entity
     */
    public static TileEntityType<?> grabTileEntity(String modId, String id) {
        return ForgeRegistries.TILE_ENTITIES.getValue(new ResourceLocation(modId, id));
    }
}
