package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {

    private static final List<Integer> SAMPLE_INPUT = List.of(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

    private final Day01 day = new Day01();
    private final List<Integer> dayInput = day.getInput();

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(7, day.resolvePart1(SAMPLE_INPUT));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(1791, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        assertEquals(5, day.resolvePart2(SAMPLE_INPUT));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(1822, day.resolvePart2(dayInput));
    }
}