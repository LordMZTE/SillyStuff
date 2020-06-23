package de.mzte.sillystuff.util;

import java.util.function.Consumer;

public class IterationHelper {
    @SafeVarargs
    public static <T> void runForAll(Consumer<T> consumer, T... input) {
        for(T element : input) {
            consumer.accept(element);
        }
    }
}
