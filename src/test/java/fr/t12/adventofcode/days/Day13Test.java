package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {

    private final Day13 day = new Day13();
    private final List<String> sampleInput = List.of(
            "6,10",
            "0,14",
            "9,10",
            "0,3",
            "10,4",
            "4,11",
            "6,0",
            "6,12",
            "4,1",
            "0,13",
            "10,12",
            "3,4",
            "3,0",
            "8,4",
            "1,10",
            "2,14",
            "8,10",
            "9,0",
            "",
            "fold along y=7",
            "fold along x=5"
    );
    private final List<String> dayInput = day.getInput();

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(17, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(695, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInput() {
        Day13.Paper paper = day.resolvePart2(sampleInput);
        List<String> expected = List.of(
                "█████",
                "█   █",
                "█   █",
                "█   █",
                "█████",
                "     ",
                "     "
        );
        assertEquals(expected, paper.toLines());
    }

    @Test
    public void testResolvePart2WithDayInput() {
        Day13.Paper paper = day.resolvePart2(dayInput);
        List<String> expected = List.of(
                " ██    ██ ████  ██  █    █  █ ███    ██ ",
                "█  █    █    █ █  █ █    █  █ █  █    █ ",
                "█       █   █  █    █    █  █ █  █    █ ",
                "█ ██    █  █   █ ██ █    █  █ ███     █ ",
                "█  █ █  █ █    █  █ █    █  █ █    █  █ ",
                " ███  ██  ████  ███ ████  ██  █     ██  "
        );
        assertEquals(expected, paper.toLines());
    }
}