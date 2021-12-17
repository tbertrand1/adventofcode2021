package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {

    private final Day17 day = new Day17();
    private final String dayInput = day.getInput();

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(45, day.resolvePart1("target area: x=20..30, y=-10..-5"));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(3_570, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInputs() {
        assertEquals(112, day.resolvePart2("target area: x=20..30, y=-10..-5"));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(1_919, day.resolvePart2(dayInput));
    }
}