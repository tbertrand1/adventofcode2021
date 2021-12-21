package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
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
    public void testResolvePart1AndPart2WithSampleInput() {
        assertEquals(Tuple.of(1_656, 195), day.resolvePart1AndPart2(sampleInput));
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        assertEquals(Tuple.of(1_721, 298), day.resolvePart1AndPart2(dayInput));
    }
}