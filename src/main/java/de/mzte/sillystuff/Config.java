package de.mzte.sillystuff;

import net.minecraftforge.common.ForgeConfigSpec;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class Config {
	public static final String CATEGORY_SILLYSTUFF = MODID;

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;

	public static ForgeConfigSpec.IntValue ACCELERATOR_BEACON_RANGE;
	public static ForgeConfigSpec.IntValue ACCELERATOR_ACCELERATION;
	public static ForgeConfigSpec.BooleanValue CONSUME_RECALL_PEARL;
	public static ForgeConfigSpec.BooleanValue CHANGE_RECALL_PEARL_LOCATION;

	static {
		COMMON_BUILDER.comment("SillyStuff Settings").push(CATEGORY_SILLYSTUFF);

		ACCELERATOR_BEACON_RANGE = COMMON_BUILDER.comment("The Range in which a beacon must be for the accelerator to work")
				.defineInRange("acceleratorBeaconRange", 10, 1, Integer.MAX_VALUE);
		ACCELERATOR_ACCELERATION = COMMON_BUILDER.comment("The Number of bonus ticks the accelerator is gonna give to its connected tile per level of speed supplied by the beacon")
				.defineInRange("acceleratorAcceleration", 10, 1, 256);
		CONSUME_RECALL_PEARL = COMMON_BUILDER.comment("Whether or not the Recall Pearl should be consumed on use")
				.define("consumeRecallPearl", true);
		CHANGE_RECALL_PEARL_LOCATION = COMMON_BUILDER.comment("Whether or not the Recall Pearl's Location Can be set Mutiple Times")
                .define("changeRecallPearlLocation", false);

		COMMON_BUILDER.pop();
		COMMON_CONFIG = COMMON_BUILDER.build();
	}
}
