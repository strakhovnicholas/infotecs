package com.straxov.util;

import com.straxov.entitiy.Worker;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Utility class for random selection and filtering operations in the city simulation.
 */
public class Utils {
    private static final Random random = new Random();

    /**
     * Returns a random element from the list, or null if the list is empty.
     *
     * @param list the list to select from
     * @return a random element or null
     */
    public static <T> T getRandom(List<T> list) {
        if (list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Returns a random free Worker from the list, or null if none are free.
     *
     * @param workers the list of workers
     * @return a free Worker or null
     */
    public static Worker getRandomFreeWorker(List<Worker> workers) {
        for (Worker w : workers) {
            if (!w.isBusy()) return w;
        }
        return null;
    }

    /**
     * Returns a random element from the list that matches the predicate, or null if none match.
     *
     * @param list      the list to select from
     * @param predicate the filter predicate
     * @return a random matching element or null
     */
    public static <T> T getRandomFree(List<T> list, Predicate<T> predicate) {
        List<T> filtered = list.stream().filter(predicate).toList();
        if (filtered.isEmpty()) return null;
        return filtered.get(random.nextInt(filtered.size()));
    }
}