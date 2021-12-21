package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {

    private final Day06 day = new Day06();
    private final List<Integer> sampleInput = List.of(3, 4, 3, 1, 2);
    private final List<Integer> dayInput = day.getInput();

    @Test
    public void testResolvePart1AndPart2WithSampleInput() {
        assertEquals(Tuple.of(5_934L, 26_984_457_539L), day.resolvePart1AndPart2(sampleInput));
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        assertEquals(Tuple.of(362_346L, 1_639_643_057_051L), day.resolvePart1AndPart2(dayInput));
    }
}