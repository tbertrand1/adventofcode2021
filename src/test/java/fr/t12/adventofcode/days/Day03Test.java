package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {

    private final Day03 day = new Day03();
    private final List<String> dayInput = day.getInput();
    private final List<String> sampleInput = List.of(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
    );

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(198, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(3_148_794, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        assertEquals(230, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(2_795_310, day.resolvePart2(dayInput));
    }
}