package de.mzte.sillystuff;

import de.mzte.sillystuff.blocks.ModBlocks;
import de.mzte.sillystuff.items.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(SillyStuff.MODID)
public class SillyStuff {
    public static final String MODID = "sillystuff";
    public static ItemGroup ITEM_GROUP;

    public SillyStuff() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::clientSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ITEM_GROUP = new ItemGroup(-1, MODID) {
            @Override
            @OnlyIn(Dist.CLIENT)
            public ItemStack createIcon() {
                return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "animal_grower")));
            }
        };

    }

    private void clientSetup(final FMLClientSetupEvent e) {
        RenderTypeLookup.setRenderLayer(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID,  "better_scaffold")), RenderType.getTranslucent());
    }
}
