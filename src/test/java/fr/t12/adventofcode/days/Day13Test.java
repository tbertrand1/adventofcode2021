package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
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
    public void testResolvePart1AndPart2WithSampleInput() {
        Tuple<Integer, Day13.Paper> results = day.resolvePart1AndPart2(sampleInput);
        assertEquals(17, results.first());
        List<String> expected = List.of(
                "█████",
                "█   █",
                "█   █",
                "█   █",
                "█████",
                "     ",
                "     "
        );
        assertEquals(expected, results.second().toLines());
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        Tuple<Integer, Day13.Paper> results = day.resolvePart1AndPart2(dayInput);
        assertEquals(695, results.first());
        List<String> expected = List.of(
                " ██    ██ ████  ██  █    █  █ ███    ██ ",
                "█  █    █    █ █  █ █    █  █ █  █    █ ",
                "█       █   █  █    █    █  █ █  █    █ ",
                "█ ██    █  █   █ ██ █    █  █ ███     █ ",
                "█  █ █  █ █    █  █ █    █  █ █    █  █ ",
                " ███  ██  ████  ███ ████  ██  █     ██  "
        );
        assertEquals(expected, results.second().toLines());
    }
}