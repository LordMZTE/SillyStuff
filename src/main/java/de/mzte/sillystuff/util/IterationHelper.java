package de.mzte.sillystuff.util;

import java.util.function.Consumer;

public class IterationHelper {
    /**
     * This method will applay a consumer to all input values
     *
     * @param consumer the consumer to use
     * @param input the values to apply to the consumer
     * @param <T> the type of the input
     */
    @SafeVarargs
    public static <T> void runForAll(Consumer<T> consumer, T... input) {
        for(T element : input)
            consumer.accept(element);
    }
}
