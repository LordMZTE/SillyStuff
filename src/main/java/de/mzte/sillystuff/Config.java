package de.mzte.sillystuff;

import net.minecraftforge.common.ForgeConfigSpec;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class Config {
	public static final String CATEGORY_SILLYSTUFF = MODID;

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;

	public static ForgeConfigSpec.IntValue ACCELERATOR_BEACON_RANGE;
	public static ForgeConfigSpec.IntValue ACCELERATOR_ACCELERATION;

	static {
		COMMON_BUILDER.comment("SillyStuff Settings").push(CATEGORY_SILLYSTUFF);

		ACCELERATOR_BEACON_RANGE = COMMON_BUILDER.comment("The Range in which a beacon must be for the accelerator to work")
				.defineInRange("acceleratorBeaconRange", 10, 1, Integer.MAX_VALUE);
		ACCELERATOR_ACCELERATION = COMMON_BUILDER.comment("The Number of bonus ticks the accelerator is gonna give to its connected tile per level of speed supplied by the beacon")
				.defineInRange("acceleratorAcceleration", 10, 1, 256);

		COMMON_BUILDER.pop();
		COMMON_CONFIG = COMMON_BUILDER.build();
	}
}
