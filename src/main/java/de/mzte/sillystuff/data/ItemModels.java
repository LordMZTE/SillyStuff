package de.mzte.sillystuff.data;

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
        //region Big Tools
        buildBigTool("wooden");
        buildBigTool("golden");
        buildBigTool("stone");
        buildBigTool("iron");
        buildBigTool("diamond");
        //endregion

        //region Other Items
        buildSimpleItem("animal_grower");
        buildSimpleItem("boiled_sweet_berries");
        buildSimpleItem("recall_pearl");

        buildSimpleBlock("better_scaffold");
        buildSimpleBlock("illuminated_better_scaffold");
        buildSimpleBlock("accelerator");
        //endregion
    }

    private void buildBigTool(String name) {
        buildSimpleItem(name + "_hammer");
        buildSimpleItem(name + "_excavator");
        buildSimpleItem(name + "_great_axe");
    }

    @Override
    public String getName() {
        return "Silly Stuff: Item Models";
    }

    private void buildSimpleItem(String registryName) {
        getBuilder(registryName)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", mcLoc("sillystuff:item/" + registryName));
    }
    private void buildSimpleBlock(String registryName) {
        getBuilder(registryName)
                .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(MODID, "block/" + registryName)));
    }
}
