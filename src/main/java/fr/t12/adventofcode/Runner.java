package fr.t12.adventofcode;

import fr.t12.adventofcode.days.*;

import java.util.List;

public class Runner {

    private static final List<Class<? extends Day<?, ?, ?>>> DAYS_CLASSES = List.of(
            Day01.class, Day02.class, Day03.class, Day04.class, Day05.class, Day06.class, Day07.class
    );

    public static void main(String[] args) {
        DAYS_CLASSES.forEach(c -> {
            try {
                c.getDeclaredConstructor().newInstance().resolveDay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
