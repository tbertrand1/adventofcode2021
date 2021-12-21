package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {

    private final Day09 day = new Day09();
    private final Day09.Grid sampleInput = new Day09.Grid(List.of("2199943210", "3987894921", "9856789892", "8767896789", "9899965678"));
    private final Day09.Grid dayInput = day.getInput();

    @Test
    public void testTesolvePart1AndPart2WithSampleInput() {
        assertEquals(Tuple.of(15, 1_134), day.resolvePart1AndPart2(sampleInput));
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        assertEquals(Tuple.of(575, 1_019_700), day.resolvePart1AndPart2(dayInput));
    }
}