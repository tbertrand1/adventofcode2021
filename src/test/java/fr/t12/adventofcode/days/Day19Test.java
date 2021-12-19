package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19Test {

    private final Day19 day = new Day19();
    private final String dayInput = day.getInput();
    private final String sampleInput = FileUtil.readFile("inputs/day19-sample.txt");

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(79, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(428, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInputs() {
        assertEquals(3_621, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(12_140, day.resolvePart2(dayInput));
    }
}