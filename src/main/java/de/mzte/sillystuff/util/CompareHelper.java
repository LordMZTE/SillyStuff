package de.mzte.sillystuff.util;

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
        Class aClass, bClass;
        aClass = a.getClass();
        bClass = b.getClass();
        return  bClass == aClass || bClass.isAssignableFrom(aClass);
    }
}
