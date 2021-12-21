package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Test {

    private final Day20 day = new Day20();
    private final List<String> dayInput = day.getInput();
    private final List<String> sampleInput = List.of(
            "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#",
            "",
            "#..#.",
            "#....",
            "##..#",
            "..#..",
            "..###"
    );

    @Test
    public void testResolvePart1AndPart2WithSampleInput() {
        assertEquals(Tuple.of(35, 3_351), day.resolvePart1AndPart2(sampleInput));
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        assertEquals(Tuple.of(5_379, 17_917), day.resolvePart1AndPart2(dayInput));
    }
}