package de.mzte.sillystuff.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

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
		registration.addIngredientInfo(new ItemStack(ForgeRegistries.ITEMS.getValue(
				new ResourceLocation(MODID, "animal_grower"))),
				VanillaTypes.ITEM,
				"jei.sillystuff.description.animal_grower");

		registration.addIngredientInfo(new ItemStack(ForgeRegistries.ITEMS.getValue(
				new ResourceLocation(MODID, "recall_pearl"))),
				VanillaTypes.ITEM,
				"jei.sillystuff.description.recall_pearl");

		registration.addIngredientInfo(new ItemStack(ForgeRegistries.ITEMS.getValue(
				new ResourceLocation(MODID, "accelerator"))),
				VanillaTypes.ITEM,
				"jei.sillystuff.description.accelerator");

		registration.addIngredientInfo(Arrays.asList(
					new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "better_scaffold"))),
					new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "illuminated_better_scaffold")))
				),
				VanillaTypes.ITEM,
				"jei.sillystuff.description.better_scaffold");
	}
}