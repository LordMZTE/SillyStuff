package de.mzte.sillystuff.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class RegistryHelper {
    public static Item grabModItem(String id) {
        return grabItem(MODID, id);
    }

    public static Item grabItem(String modId, String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, id));
    }

    public static Block grabModBlock(String id) {
        return grabBlock(MODID, id);
    }

    public static Block grabBlock(String modId, String id) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modId, id));
    }

    public static TileEntityType<?> grabModTileEntity(String id) {
        return grabTileEntity(MODID, id);
    }

    public static TileEntityType<?> grabTileEntity(String modId, String id) {
        return ForgeRegistries.TILE_ENTITIES.getValue(new ResourceLocation(modId, id));
    }
}
