package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import fr.t12.adventofcode.common.Tuple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19Test {

    private final Day19 day = new Day19();
    private final String dayInput = day.getInput();
    private final String sampleInput = FileUtil.readFile("inputs/day19-sample.txt");

    @Test
    public void testResolvePart1AndPart2WithSampleInput() {
        assertEquals(Tuple.of(79, 3_621), day.resolvePart1AndPart2(sampleInput));
    }

    @Test
    public void testTesolvePart1AndPart2WithDayInput() {
        assertEquals(Tuple.of(428, 12_140), day.resolvePart1AndPart2(dayInput));
    }
}