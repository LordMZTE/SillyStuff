package de.mzte.sillystuff.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseLootTableProvider extends LootTableProvider {
    private final DataGenerator gen;
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.gen = dataGeneratorIn;
    }

    protected LootTable.Builder createSelfDropTable(Block block) {

        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(block))
                .acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder createCopyInventoryDropTable(Block block) {

        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(block))
                .acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
                .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                        .addOperation("inv", "BlockEntityTag.inv", CopyNbt.Action.REPLACE))
                .acceptFunction(SetContents.func_215920_b()
                        .func_216075_a(DynamicLootEntry.func_216162_a((new ResourceLocation("minecraft", "contents"))))
                        .acceptCondition(SurvivesExplosion.builder()));
        return LootTable.builder().addLootPool(builder);
    }

    protected abstract void addTables();

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {

        Path outputFolder = this.gen.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException ignored) {

            }
        });
    }

    @Override
    public void act(DirectoryCache cache) {

        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    @Override
    public String getName() {
        return "SillyStuff: LootTables";
    }
}
