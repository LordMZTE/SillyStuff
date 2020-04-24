package de.mzte.sillystuff.data;

import com.google.common.collect.ObjectArrays;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "better_scaffold")), model -> ObjectArrays.concat(
                ConfiguredModel.allYRotations(model, 0, false),
                ConfiguredModel.allYRotations(model, 180, false),
                ConfiguredModel.class));
    }
}
