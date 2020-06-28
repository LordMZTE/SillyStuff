package de.mzte.sillystuff.tile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class ModTiles {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);

	public static void register(IEventBus modEventBus) {
		TILE_ENTITIES.register(modEventBus);

		TILE_ENTITIES.register("accelerator", () -> TileEntityType.Builder.create(AcceleratorTile::new,
				ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "accelerator"))).build(null));
	}
}
