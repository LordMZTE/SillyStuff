package de.mzte.sillystuff.data;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "Silly Stuff: Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "animal_grower")), 2)
                .addIngredient(Tags.Items.CROPS_CARROT)
                .addIngredient(Tags.Items.CROPS_POTATO)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .addIngredient(Tags.Items.CROPS_BEETROOT)
                .addIngredient(Items.MELON_SLICE)
                .setGroup(MODID)
                .addCriterion("seeds", hasItem(Items.WHEAT_SEEDS))
                .build(consumer, new ResourceLocation(MODID, "animal_grower_potato"));

        ShapelessRecipeBuilder.shapelessRecipe(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "animal_grower")), 4)
                .addIngredient(Tags.Items.CROPS_CARROT)
                .addIngredient(Items.POISONOUS_POTATO)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .addIngredient(Tags.Items.CROPS_BEETROOT)
                .addIngredient(Items.MELON_SLICE)
                .setGroup(MODID)
                .addCriterion("poisoned_potato", hasItem(Items.POISONOUS_POTATO))
                .build(consumer,  new ResourceLocation(MODID, "animal_grower_poisonous_potato"));
    }
}
