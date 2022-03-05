package de.mzte.sillystuff.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class SerializationHelper {
    /**
     * Deserialized a {@link ResourceLocation} that has been Serialized with {@link ResourceLocation#toString()}
     *
     * @param loc the serialized {@link ResourceLocation}
     * @return the deserialized {@link ResourceLocation}
     */
    public static ResourceLocation deserializeResourceLocation(String loc) {
        String[] name = loc.split(":");
        return new ResourceLocation(name[0], name[1]);
    }

    public static String serializeDimension(ServerWorld w) {
        return w.getDimensionKey().getLocation().toString();
    }

    public static ServerWorld deserializeDimension(String s, MinecraftServer serv) {
        return serv.getWorld(
                RegistryKey.getOrCreateKey(
                        Registry.WORLD_KEY,
                        deserializeResourceLocation(s)
                )
        );
    }
}
