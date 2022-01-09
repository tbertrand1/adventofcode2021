package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24Test {

    private final Day24 day = new Day24();

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(99_893_999_291_967L, day.resolvePart1(null));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(34_171_911_181_211L, day.resolvePart2(null));
    }
}
