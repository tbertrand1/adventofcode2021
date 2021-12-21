package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;

import java.util.Arrays;
import java.util.List;

public class Day06 extends Day<List<Integer>, Long, Long> {

    private static final int NB_DAYS_PART_1 = 80;
    private static final int NB_DAYS_PART_2 = 256;

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
    protected Tuple<Long, Long> resolvePart1AndPart2(List<Integer> input) {
        long[] countByNbDays = new long[9];
        for (Integer days : input) {
            countByNbDays[days]++;
        }

        int day = 0;
        long sumPart1 = 0;
        while (day <= NB_DAYS_PART_2) {
            long[] nextCountByNbDays = new long[9];
            nextCountByNbDays[6] = countByNbDays[0];
            nextCountByNbDays[8] = countByNbDays[0];
            for (int i = 1; i <= 8; i++) {
                nextCountByNbDays[i - 1] += countByNbDays[i];
            }
            countByNbDays = nextCountByNbDays;
            if (day == NB_DAYS_PART_1) {
                sumPart1 = sumCounts(countByNbDays);
            }
            day++;
        }

        long sumPart2 = sumCounts(countByNbDays);
        return Tuple.of(sumPart1, sumPart2);
    }

    private long sumCounts(long[] countByNbDays) {
        long totalCount = 0;
        for (int i = 0; i < 8; i++) {
            totalCount += countByNbDays[i];
        }
        return totalCount;
    }
}
