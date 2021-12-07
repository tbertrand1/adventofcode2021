package fr.t12.adventofcode.days;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day07 extends Day<List<Integer>, Integer, Integer> {

    public Day07() {
        super(7);
    }

    @Override
    protected List<Integer> getInput() {
        return Arrays.stream(readInputAsString().split(",")).map(Integer::parseInt).toList();
    }

    @Override
    protected Integer resolvePart1(List<Integer> input) {
        return findMinFuel(input, (p1, p2) -> Math.abs(p1 - p2));
    }

    @Override
    protected Integer resolvePart2(List<Integer> input) {
        return findMinFuel(input, (p1, p2) -> {
            int distance = Math.abs(p1 - p2);
            return distance * (distance - 1) / 2 + distance;
        });
    }

    private Integer findMinFuel(List<Integer> input, BiFunction<Integer, Integer, Integer> calculateFuelFn) {
        int min = input.stream().mapToInt(Integer::intValue).min().orElse(0);
        int max = input.stream().mapToInt(Integer::intValue).max().orElse(Integer.MAX_VALUE);

        int minFuel = Integer.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            final int position = i;
            int fuel = input.stream().mapToInt(p -> calculateFuelFn.apply(position, p)).sum();
            if (fuel < minFuel) {
                minFuel = fuel;
            }
        }
        return minFuel;
    }
}
