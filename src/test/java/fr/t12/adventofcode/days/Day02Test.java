package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {

    private final Day02 day = new Day02();
    private final List<Day02.Action> dayInput = day.getInput();
    private final List<Day02.Action> sampleInput = Stream.of(
                    "forward 5",
                    "down 5",
                    "forward 8",
                    "up 3",
                    "down 8",
                    "forward 2"
            )
            .map(Day02.Action::parse)
            .collect(Collectors.toList());

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(150, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(1_604_850, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        assertEquals(900, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(1_685_186_100, day.resolvePart2(dayInput));
    }
}