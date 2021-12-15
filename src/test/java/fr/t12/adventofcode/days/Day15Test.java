package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {

    private final Day15 day = new Day15();
    private final List<String> sampleInput = List.of(
            "1163751742",
            "1381373672",
            "2136511328",
            "3694931569",
            "7463417111",
            "1319128137",
            "1359912421",
            "3125421639",
            "1293138521",
            "2311944581"
    );
    private final List<String> dayInput = day.getInput();

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(40, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(363, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        assertEquals(315, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(2_835, day.resolvePart2(dayInput));
    }
}