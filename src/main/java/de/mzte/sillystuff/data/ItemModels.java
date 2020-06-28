package de.mzte.sillystuff.data;

import de.mzte.sillystuff.util.IterationHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Big Tools
        IterationHelper.runForAll(this::buildBigTool,
                "wooden",
                "golden",
                "stone",
                "iron",
                "diamond");

        //Handheld Rods
        buildParentItem("emerald_on_a_stick", "item/handheld_rod");

        //Other Items
        IterationHelper.runForAll(this::buildSimpleItem,
                "animal_grower",
                "boiled_sweet_berries",
                "recall_pearl");

        //Blocks
        IterationHelper.runForAll(this::buildSimpleBlock,
                "better_scaffold",
                "illuminated_better_scaffold",
                "accelerator");

    }

    private void buildBigTool(String name) {
        IterationHelper.runForAll(s -> this.buildParentItem(s, "item/handheld"),
                name + "_hammer",
                name + "_excavator",
                name + "_great_axe");
    }

    @Override
    public String getName() {
        return "Silly Stuff: Item Models";
    }

    private void buildParentItem(String registryName, String parent) {
        getBuilder(registryName)
                .parent(new ModelFile.UncheckedModelFile(parent))
                .texture("layer0", mcLoc("sillystuff:item/" + registryName));
    }

    private void buildSimpleItem(String registryName) {
        buildParentItem(registryName, "item/generated");
    }




    private void buildSimpleBlock(String registryName) {
        getBuilder(registryName)
                .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(MODID, "block/" + registryName)));
    }
}
