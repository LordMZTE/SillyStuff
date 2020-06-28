package de.mzte.sillystuff.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class PositionHelper {
    /**
     * Gets a {@link BlockPos} from a {@link Vector3d} by rounding the coordinates
     *
     * @param vec the {@link Vector3d} to get the {@link BlockPos} from
     * @return the {@link BlockPos}
     */
    public static BlockPos blockPosFromVec3d(Vector3d vec) {
        return new BlockPos(
                Math.round(vec.getX()),
                Math.round(vec.getY()),
                Math.round(vec.getZ())
        );
    }

    /**
     * Gets a {@link Vector3d} from a {@link BlockPos}
     *
     * @param pos the {@link BlockPos} to get the {@link Vector3d} from
     * @return the {@link Vector3d}
     */
    public static Vector3d vec3dFromBlockPos(BlockPos pos) {
        return new Vector3d(
                pos.getX(),
                pos.getY(),
                pos.getZ()
        );
    }
}
