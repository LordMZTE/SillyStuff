package de.mzte.sillystuff.data;

import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

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
        foodRecipes(600, "campfire", CookingRecipeSerializer.CAMPFIRE_COOKING, consumer);
        foodRecipes(100, "smoking", CookingRecipeSerializer.SMOKING, consumer);
        foodRecipes(200, "smelting", CookingRecipeSerializer.SMELTING, consumer);

        ShapedRecipeBuilder.shapedRecipe(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "better_scaffold")), 16)
                .key('#', Items.STICK)
                .patternLine("# #")
                .patternLine(" # ")
                .patternLine("# #")
                .addCriterion("stick", hasItem(Items.STICK))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "animal_grower")), 2)
                .addIngredient(Tags.Items.CROPS_CARROT)
                .addIngredient(Tags.Items.CROPS_POTATO)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .addIngredient(Tags.Items.CROPS_BEETROOT)
                .addIngredient(Items.MELON_SLICE)
                .setGroup(MODID + ":animal_grower")
                .addCriterion("seeds", hasItem(Items.WHEAT_SEEDS))
                .build(consumer, new ResourceLocation(MODID, "animal_grower_potato"));

        ShapelessRecipeBuilder.shapelessRecipe(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "animal_grower")), 4)
                .addIngredient(Tags.Items.CROPS_CARROT)
                .addIngredient(Items.POISONOUS_POTATO)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .addIngredient(Tags.Items.CROPS_BEETROOT)
                .addIngredient(Items.MELON_SLICE)
                .setGroup(MODID + ":animal_grower")
                .addCriterion("poisoned_potato", hasItem(Items.POISONOUS_POTATO))
                .build(consumer,  new ResourceLocation(MODID, "animal_grower_poisonous_potato"));

        ShapedRecipeBuilder.shapedRecipe(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "accelerator")))
                .key('D', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .key('S', Tags.Items.STONE)
                .key('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .key('Q', Tags.Items.STORAGE_BLOCKS_QUARTZ)
                .patternLine("SSS")
                .patternLine("QRD")
                .patternLine("SSS")
                .addCriterion("beacon", hasItem(Items.BEACON))
                .build(consumer);
    }
    private void foodRecipes(int time, String methodName, CookingRecipeSerializer<?> serializer, Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.cookingRecipe(
                Ingredient.fromItems(Items.SWEET_BERRIES),
                ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID, "boiled_sweet_berries")),
                0.1F,
                time,
                serializer)
                .addCriterion("has_berries", hasItem(Items.SWEET_BERRIES))
                .build(consumer, "boiled_sweet_berries_" + methodName);
    }
}
