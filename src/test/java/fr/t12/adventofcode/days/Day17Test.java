package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {

    private final Day17 day = new Day17();
    private final String dayInput = day.getInput();

    @Test
    public void testResolvePart1AndPart2WithSampleInput() {
        assertEquals(Tuple.of(45, 112), day.resolvePart1AndPart2("target area: x=20..30, y=-10..-5"));
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        assertEquals(Tuple.of(3_570, 1_919), day.resolvePart1AndPart2(dayInput));
    }
}