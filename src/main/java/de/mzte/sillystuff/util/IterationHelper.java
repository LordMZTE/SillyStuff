package de.mzte.sillystuff.util;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class IterationHelper {
    /**
     * This method will apply a consumer to all input values
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

    /**
     * Will check if any of the input elements match the predicate
     *
     * @param predicate the predicate to check against
     * @param iter the input elements to be checked
     * @param <T> the type of elements
     * @return whether any of the input elements matched the predicate
     */
    public static <T> boolean anyMatch(Predicate<T> predicate, Iterable<T> iter) {
        for(T t : iter)
            if(predicate.test(t))
                return true;
        return false;
    }

    /**
     * Will check if all of the input elements match the predicate
     *
     * @param predicate the predicate to check against
     * @param iter the input elements to be checked
     * @param <T> the type of elements
     * @return whether all of the input elements matched the predicate
     */
    public static <T> boolean allMatch(Predicate<T> predicate, Iterable<T> iter) {
        for(T t : iter)
            if(!predicate.test(t))
                return false;
        return true;
    }
}
