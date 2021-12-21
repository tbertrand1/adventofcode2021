package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Test {

    private final Day21 day = new Day21();
    private final List<String> dayInput = day.getInput();
    private final List<String> sampleInput = List.of(
            "Player 1 starting position: 4",
            "Player 2 starting position: 8"
    );

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(739_785, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(906_093, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInputs() {
        assertEquals(444_356_092_776_315L, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(274_291_038_026_362L, day.resolvePart2(dayInput));
    }
}