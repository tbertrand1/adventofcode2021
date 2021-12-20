package fr.t12.adventofcode.days;

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
    public void testResolvePart1WithSampleInput() {
        assertEquals(35, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(5_379, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInputs() {
        assertEquals(3_351, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(17_917, day.resolvePart2(dayInput));
    }
}