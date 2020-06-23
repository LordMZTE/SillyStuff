package de.mzte.sillystuff.data;

import de.mzte.sillystuff.util.IterationHelper;
import de.mzte.sillystuff.util.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        IterationHelper.runForAll(b -> {
            Block block = RegistryHelper.grabModBlock(b);
            lootTables.put(block, createSelfDropTable(block));
        },
                "better_scaffold",
                "illuminated_better_scaffold",
                "accelerator");
    }
}
