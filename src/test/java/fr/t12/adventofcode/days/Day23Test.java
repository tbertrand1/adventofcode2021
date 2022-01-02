package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day23Test {

    private final Day23 day = new Day23();
    private final List<String> dayInput = day.getInput();
    private final List<String> sampleInput = FileUtil.readFileLines("inputs/day23-sample.txt");

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(12_521, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(11_608, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        assertEquals(44_169, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(46_754, day.resolvePart2(dayInput));
    }
}