package de.mzte.sillystuff.data;

import net.minecraft.data.DataGenerator;
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
        getBuilder("animal_grower")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", mcLoc("sillystuff:item/animal_grower"));
    }

    @Override
    public String getName() {
        return "Silly Stuff: Item Models";
    }
}
