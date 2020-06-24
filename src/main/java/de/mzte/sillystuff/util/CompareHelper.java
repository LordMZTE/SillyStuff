package de.mzte.sillystuff.util;

import net.minecraft.util.math.BlockPos;

public class CompareHelper {
    /**
     * Returns True If a extends b
     *
     * @param a The object that b must be a superclass of
     * @param b The object that a must be a subclass of
     * @return If a extends b
     */
    public static boolean objectExtends(Object a, Object b) {
        if(a == null || b == null)
            return false;
        Class<?> aClass, bClass;
        aClass = a.getClass();
        bClass = b.getClass();
        return bClass == aClass || bClass.isAssignableFrom(aClass);
    }

    /**
     * True if a is in range of b
     *
     * @param a the first position
     * @param b the second position
     * @param range the range the positions must be in
     * @return if the positions are in range of each other
     */
    public static boolean isInCubeRange(BlockPos a, BlockPos b, int range) {
        return Math.abs(a.getX() - b.getX()) <= range &&
                Math.abs(a.getY() - b.getY()) <= range &&
                Math.abs(a.getZ() - b.getZ()) <= range;
    }
}
