package de.mzte.sillystuff.data;

import de.mzte.sillystuff.util.IterationHelper;
import de.mzte.sillystuff.util.RegistryHelper;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

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
        //region Big Tools
        bigToolRecipes(consumer, ItemTags.LOGS, ItemTags.PLANKS, "wooden", ItemTags.PLANKS);
        bigToolRecipes(consumer, Tags.Items.STORAGE_BLOCKS_GOLD, Tags.Items.INGOTS_GOLD, "golden", Tags.Items.INGOTS_GOLD);
        bigToolRecipes(consumer, Tags.Items.STONE, Tags.Items.COBBLESTONE, "stone", Tags.Items.COBBLESTONE);
        bigToolRecipes(consumer, Tags.Items.STORAGE_BLOCKS_IRON, Tags.Items.INGOTS_IRON, "iron", Tags.Items.INGOTS_IRON);
        bigToolRecipes(consumer, Tags.Items.STORAGE_BLOCKS_DIAMOND, Tags.Items.GEMS_DIAMOND, "diamond", Tags.Items.GEMS_DIAMOND);

        //Netherite tools
        IterationHelper.runForAll(item -> SmithingRecipeBuilder.smithingRecipe(
                Ingredient.fromItems(RegistryHelper.grabModItem("diamond_" + item)),
                Ingredient.fromItems(Items.NETHERITE_INGOT),
                RegistryHelper.grabModItem("netherite_" + item)
                ).addCriterion("has_netherite_ingot", hasItem(Items.NETHERITE_INGOT))
                        .build(consumer, new ResourceLocation(MODID, "netherite_" + item)),
                "hammer",
                "excavator",
                "great_axe");
        //endregion

        //region Food Items
        foodRecipes(600, "campfire", CookingRecipeSerializer.CAMPFIRE_COOKING, consumer);
        foodRecipes(100, "smoking", CookingRecipeSerializer.SMOKING, consumer);
        foodRecipes(200, "smelting", CookingRecipeSerializer.SMELTING, consumer);
        //endregion

        //region Other Items
        ShapedRecipeBuilder.shapedRecipe(RegistryHelper.grabModItem("better_scaffold"), 8)
                .key('#', Tags.Items.RODS_WOODEN)
                .patternLine("# #")
                .patternLine(" # ")
                .patternLine("# #")
                .addCriterion("stick", hasItem(Tags.Items.RODS_WOODEN))
                .setGroup(MODID + ":better_scaffold")
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RegistryHelper.grabModItem("illuminated_better_scaffold"), 8)
                .key('#', Tags.Items.RODS_WOODEN)
                .key('G', Tags.Items.DUSTS_GLOWSTONE)
                .patternLine("# #")
                .patternLine(" G ")
                .patternLine("# #")
                .addCriterion("glowstone", hasItem(Tags.Items.DUSTS_GLOWSTONE))
                .setGroup(MODID + ":better_scaffold")
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(RegistryHelper.grabModItem("animal_grower"), 2)
                .addIngredient(Tags.Items.CROPS_CARROT)
                .addIngredient(Tags.Items.CROPS_POTATO)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .addIngredient(Tags.Items.CROPS_BEETROOT)
                .addIngredient(Items.MELON_SLICE)
                .setGroup(MODID + ":animal_grower")
                .addCriterion("seeds", hasItem(Items.WHEAT_SEEDS))
                .build(consumer, new ResourceLocation(MODID, "animal_grower_potato"));

        ShapelessRecipeBuilder.shapelessRecipe(RegistryHelper.grabModItem("recall_pearl"))
                .addIngredient(Tags.Items.ENDER_PEARLS)
                .addIngredient(Items.CHORUS_FRUIT)
                .addCriterion("pearl", hasItem(Tags.Items.ENDER_PEARLS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(RegistryHelper.grabModItem("animal_grower"), 4)
                .addIngredient(Tags.Items.CROPS_CARROT)
                .addIngredient(Items.POISONOUS_POTATO)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .addIngredient(Tags.Items.CROPS_BEETROOT)
                .addIngredient(Items.MELON_SLICE)
                .setGroup(MODID + ":animal_grower")
                .addCriterion("poisoned_potato", hasItem(Items.POISONOUS_POTATO))
                .build(consumer, new ResourceLocation(MODID, "animal_grower_poisonous_potato"));

        ShapedRecipeBuilder.shapedRecipe(RegistryHelper.grabModItem("accelerator"))
                .key('D', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .key('S', Tags.Items.STONE)
                .key('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .key('Q', Tags.Items.STORAGE_BLOCKS_QUARTZ)
                .patternLine("SSS")
                .patternLine("QRD")
                .patternLine("SSS")
                .addCriterion("beacon", hasItem(Items.BEACON))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RegistryHelper.grabModItem("emerald_on_a_stick"))
                .key('E', Tags.Items.GEMS_EMERALD)
                .key('F', Items.FISHING_ROD)
                .patternLine("EEE")
                .patternLine("EFE")
                .patternLine("EEE")
                .addCriterion("emerald", hasItem(Tags.Items.GEMS_EMERALD))
                .build(consumer);
        //endregion
    }

    private void foodRecipes(int time, String methodName, CookingRecipeSerializer<?> serializer, Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.cookingRecipe(
                Ingredient.fromItems(Items.SWEET_BERRIES),
                RegistryHelper.grabModItem("boiled_sweet_berries"),
                0.1F,
                time,
                serializer)
                .addCriterion("has_berries", hasItem(Items.SWEET_BERRIES))
                .build(consumer, new ResourceLocation(MODID, "boiled_sweet_berries_" + methodName));
    }

    private void bigToolRecipes(Consumer<IFinishedRecipe> consumer,
                                Ingredient block,
                                Ingredient item,
                                String itemName,
                                ItemPredicate unlockItem) {
        //HAMMER
        ShapedRecipeBuilder.shapedRecipe(RegistryHelper.grabModItem(itemName + "_hammer"))
                .key('B', block)
                .key('I', item)
                .key('S', Tags.Items.RODS_WOODEN)
                .patternLine("BIB")
                .patternLine(" S ")
                .patternLine(" S ")
                .addCriterion("has_item", hasItem(unlockItem))
                .build(consumer);
        //EXCAVATOR
        ShapedRecipeBuilder.shapedRecipe(RegistryHelper.grabModItem(itemName + "_excavator"))
                .key('B', block)
                .key('I', item)
                .key('S', Tags.Items.RODS_WOODEN)
                .patternLine("IBI")
                .patternLine(" S ")
                .patternLine(" S ")
                .addCriterion("has_item", hasItem(unlockItem))
                .build(consumer);
        //GREAT AXE
        ShapedRecipeBuilder.shapedRecipe(RegistryHelper.grabModItem(itemName + "_great_axe"))
                .key('B', block)
                .key('I', item)
                .key('S', Tags.Items.RODS_WOODEN)
                .patternLine("BI")
                .patternLine("IS")
                .patternLine(" S")
                .addCriterion("has_item", hasItem(unlockItem))
                .build(consumer);
    }

    private void bigToolRecipes(Consumer<IFinishedRecipe> consumer, ITag.INamedTag<Item> block, ITag.INamedTag<Item> item, String itemName, ITag.INamedTag<Item> unlockItem) {
        bigToolRecipes(consumer,
                Ingredient.fromTag(block),
                Ingredient.fromTag(item),
                itemName,
                ItemPredicate.Builder.create()
                        .tag(unlockItem).build());
    }
}
