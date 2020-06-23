package de.mzte.sillystuff.plugins.jei;

import de.mzte.sillystuff.util.IterationHelper;
import de.mzte.sillystuff.util.RegistryHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

import static de.mzte.sillystuff.SillyStuff.MODID;

@JeiPlugin
public class SillyStuffJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IterationHelper.runForAll(s -> registration.addIngredientInfo(
                new ItemStack(RegistryHelper.grabModItem(s)),
                VanillaTypes.ITEM,
                "jei.sillystuff.description." + s
                ),
                "animal_grower",
                "recall_pearl",
                "accelerator",
                "emerald_on_a_stick"
        );

        registration.addIngredientInfo(
                Arrays.asList(
                        new ItemStack(RegistryHelper.grabModItem("better_scaffold")),
                        new ItemStack(RegistryHelper.grabModItem("illuminated_better_scaffold"))
                ),
                VanillaTypes.ITEM,
                "jei.sillystuff.description.better_scaffold"
        );
    }
}