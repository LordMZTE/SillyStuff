package de.mzte.sillystuff.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class LootTables extends BaseLootTableProvider {
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        Block betterScaffold = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "better_scaffold"));
        lootTables.put(betterScaffold, createSelfDropTable(betterScaffold));
        Block illuminatedBetterScaffold = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "illuminated_better_scaffold"));
        lootTables.put(illuminatedBetterScaffold, createSelfDropTable(illuminatedBetterScaffold));
        Block accelerator = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, "accelerator"));
        lootTables.put(accelerator, createSelfDropTable(accelerator));
    }
}
