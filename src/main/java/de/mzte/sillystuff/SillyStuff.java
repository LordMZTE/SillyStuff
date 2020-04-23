package de.mzte.sillystuff;

import de.mzte.sillystuff.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(SillyStuff.MODID)
public class SillyStuff {
    public static final String MODID = "sillystuff";
    public static ItemGroup ITEM_GROUP;
    public  static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);

    public SillyStuff() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(modEventBus);
        ModItems.register();

        ITEM_GROUP = new ItemGroup(-1, MODID) {
            @Override
            @OnlyIn(Dist.CLIENT)
            public ItemStack createIcon() {
                return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "animal_grower")));
            }
        };
    }
}
