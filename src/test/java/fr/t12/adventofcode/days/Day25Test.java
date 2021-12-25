package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Test {

    private final Day25 day = new Day25();
    private final List<String> dayInput = day.getInput();
    private final List<String> sampleInput = List.of(
            "v...>>.vv>",
            ".vv>>.vv..",
            ">>.>v>...v",
            ">>v>>.>.v.",
            "v>v.vv.v..",
            ">.>>..v...",
            ".vv..>.>v.",
            "v.v..>>v.v",
            "....v..v.>"
    );

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(58, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(518, day.resolvePart1(dayInput));
    }
}
