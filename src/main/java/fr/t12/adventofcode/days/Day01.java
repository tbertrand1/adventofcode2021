package fr.t12.adventofcode.days;

import java.util.List;

public class Day01 extends Day<List<Integer>, Integer, Integer> {

    public Day01() {
        super(1);
    }

    @Override
    public List<Integer> getInput() {
        return readInputAsListOfInteger();
    }

    @Override
    public Integer resolvePart1(List<Integer> input) {
        int nbIncreased = 0;
        int previousMeasure = input.get(0);
        for (int i = 1; i < input.size(); i++) {
            int currentMeasure = input.get(i);
            if (currentMeasure > previousMeasure) {
                nbIncreased++;
            }
            previousMeasure = currentMeasure;
        }
        return nbIncreased;
    }

    @Override
    public Integer resolvePart2(List<Integer> input) {
        int nbSumIncreased = 0;
        int previousSum = input.get(0) + input.get(1) + input.get(2);
        for (int i = 3; i < input.size(); i++) {
            int currentSum = previousSum + input.get(i) - input.get(i - 3);
            if (currentSum > previousSum) {
                nbSumIncreased++;
            }
            previousSum = currentSum;
        }
        return nbSumIncreased;
    }
}
