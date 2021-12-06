package fr.t12.adventofcode.days;

import java.util.Arrays;
import java.util.List;

public class Day06 extends Day<List<Integer>, Long, Long> {

    public Day06() {
        super(6);
    }

    @Override
    protected List<Integer> getInput() {
        return Arrays.stream(readInputAsString().split(","))
                .map(Integer::parseInt)
                .toList();
    }

    @Override
    protected Long resolvePart1(List<Integer> input) {
        return simulateDays(input, 80);
    }

    @Override
    protected Long resolvePart2(List<Integer> input) {
        return simulateDays(input, 256);
    }

    protected Long simulateDays(List<Integer> input, int nbDays) {
        long[] countByNbDays = new long[9];
        for (Integer days : input) {
            countByNbDays[days]++;
        }

        int day = 0;
        while (day <= nbDays) {
            long[] nextCountByNbDays = new long[9];
            nextCountByNbDays[6] = countByNbDays[0];
            nextCountByNbDays[8] = countByNbDays[0];
            for (int i = 1; i <= 8; i++) {
                    nextCountByNbDays[i - 1] += countByNbDays[i];
            }
            countByNbDays = nextCountByNbDays;
            day++;
        }

        long totalCount = 0;
        for (int i = 0; i < 8; i++) {
            totalCount += countByNbDays[i];
        }
        return totalCount;
    }
}
