package fr.t12.adventofcode;

import fr.t12.adventofcode.days.Day;
import fr.t12.adventofcode.days.Day01;

import java.util.List;

public class Runner {

    private static final List<Class<? extends Day<?, ?, ?>>> DAYS_CLASSES = List.of(
            Day01.class
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
