package fr.t12.adventofcode;

import fr.t12.adventofcode.days.*;

import java.util.List;

public class DaysRunner {

    public static final List<Class<? extends Day<?, ?, ?>>> DAYS_CLASSES = List.of(
            Day01.class, Day02.class, Day03.class, Day04.class, Day05.class, Day06.class, Day07.class, Day08.class,
            Day09.class, Day10.class, Day11.class, Day12.class, Day13.class, Day14.class, Day15.class, Day16.class,
            Day17.class, Day18.class, Day19.class, Day20.class, Day21.class, Day22.class, Day23.class, Day24.class,
            Day25.class
    );

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int nbDays = 0;
        for (var clazz : DAYS_CLASSES) {
            try {
                clazz.getDeclaredConstructor().newInstance().resolveDay();
                nbDays++;
            } catch (Exception e) {
                System.err.printf("Day %d failed", nbDays + 1);
                e.printStackTrace();
            }
        }
        System.out.printf("\nTotal time: %d ms (%d days)\n", System.currentTimeMillis() - start, nbDays);
    }
}
