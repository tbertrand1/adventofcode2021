package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {

    private final Day04 day = new Day04();
    private final List<String> sampleInput = FileUtil.readFileLines("inputs/day04-sample.txt");
    private final List<String> dayInput = day.getInput();

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(4_512, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(72_770, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        assertEquals(1_924, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(13_912, day.resolvePart2(dayInput));
    }
}