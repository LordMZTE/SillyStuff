package de.mzte.sillystuff.data;

import de.mzte.sillystuff.util.IterationHelper;
import de.mzte.sillystuff.util.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

public class TagsItem extends TagsProvider<Item> {

    public TagsItem(DataGenerator generatorIn) {
        super(generatorIn, Registry.ITEM);
    }

    @Override
    protected void registerTags() {
        TagsProvider.Builder<Item> builder = this.func_240522_a_(ItemTags.field_232903_N_);

        IterationHelper.runForAll(str -> builder.func_240532_a_(RegistryHelper.grabModItem(str)),
                "golden_hammer",
                "golden_excavator",
                "golden_great_axe"
        );
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/items/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Silly Stuff: Item Tags";
    }
}
