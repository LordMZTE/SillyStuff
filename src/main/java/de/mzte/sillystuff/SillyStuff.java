package de.mzte.sillystuff;

import de.mzte.sillystuff.blocks.ModBlocks;
import de.mzte.sillystuff.items.ModItems;
import de.mzte.sillystuff.tile.ModTiles;
import de.mzte.sillystuff.util.RegistryHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SillyStuff.MODID)
public class SillyStuff {
    public static final String MODID = "sillystuff";
    public static ItemGroup ITEM_GROUP;

    public SillyStuff() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::clientSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModTiles.register(modEventBus);

        ITEM_GROUP = new ItemGroup(-1, MODID) {
            @Override
            @OnlyIn(Dist.CLIENT)
            public ItemStack createIcon() {
                return new ItemStack(RegistryHelper.grabModItem("recall_pearl"));
            }
        };

    }

    private void clientSetup(final FMLClientSetupEvent e) {
        RenderTypeLookup.setRenderLayer(RegistryHelper.grabModBlock("illuminated_better_scaffold"), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHelper.grabModBlock("better_scaffold"), RenderType.getCutout());
    }

}
