package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    private final Day11 day = new Day11();
    private final List<String> sampleInput = List.of(
            "5483143223",
            "2745854711",
            "5264556173",
            "6141336146",
            "6357385478",
            "4167524645",
            "2176841721",
            "6882881134",
            "4846848554",
            "5283751526"
    );
    private final List<String> dayInput = day.getInput();

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(1_656, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(1_721, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        assertEquals(195, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(298, day.resolvePart2(dayInput));
    }
}