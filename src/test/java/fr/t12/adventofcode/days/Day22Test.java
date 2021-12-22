package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import fr.t12.adventofcode.common.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Test {

    private final Day22 day = new Day22();
    private final List<Day22.Step> dayInput = day.getInput();
    private final List<Day22.Step> sampleInputPart1 = FileUtil.readFileLines("inputs/day22-sample-part1.txt").stream()
            .map(Day22.Step::parse)
            .toList();
    private final List<Day22.Step> sampleInputPart2 = FileUtil.readFileLines("inputs/day22-sample-part2.txt").stream()
            .map(Day22.Step::parse)
            .toList();

    @Test
    public void testResolvePart1AndPart2WithSamplesInput() {
        Tuple<Long, Long> results1 = day.resolvePart1AndPart2(sampleInputPart1);
        assertEquals(590_784L, results1.first());

        Tuple<Long, Long> results = day.resolvePart1AndPart2(sampleInputPart2);
        assertEquals(474_140L, results.first());
        assertEquals(2_758_514_936_282_235L, results.second());
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        Tuple<Long, Long> results = day.resolvePart1AndPart2(dayInput);
        assertEquals(623_748L, results.first());
        assertEquals(1_227_345_351_869_476L, results.second());
    }
}