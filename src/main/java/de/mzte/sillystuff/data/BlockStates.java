package de.mzte.sillystuff.data;

import com.google.common.collect.ObjectArrays;
import de.mzte.sillystuff.util.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        generateBasicBlock("better_scaffold");
        generateBasicBlock("illuminated_better_scaffold");
    }

    private void generateBasicBlock(String id) {
        simpleBlock(RegistryHelper.grabModBlock(id), model -> ObjectArrays.concat(
                ConfiguredModel.allYRotations(model, 0, false),
                ConfiguredModel.allYRotations(model, 180, false),
                ConfiguredModel.class));
    }
}
