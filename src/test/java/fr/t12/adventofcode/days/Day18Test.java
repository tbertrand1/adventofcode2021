package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test {

    private static final List<String> sampleInput = List.of(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
    );
    private final Day18 day = new Day18();
    private final List<String> dayInput = day.getInput();

    @Test
    public void testReduceWithSampleInput() {
        Day18.SnailfishNumber number = Day18.SnailfishNumber.parse("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", number.reduce().toString());
    }

    @Test
    public void testSumWithSampleInput() {
        Day18.SnailfishNumber sum = Stream.of(
                        "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                        "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                        "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                        "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                        "[7,[5,[[3,8],[1,4]]]]",
                        "[[2,[2,2]],[8,[8,1]]]",
                        "[2,9]",
                        "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                        "[[[5,[7,4]],7],1]",
                        "[[[[4,2],2],6],[8,7]]"
                )
                .map(Day18.SnailfishNumber::parse)
                .reduce(Day18.SnailfishNumber::sum)
                .orElse(null);

        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", sum.toString());
    }

    @Test
    public void testResolvePart1WithSampleInput() {
        assertEquals(4_140L, day.resolvePart1(sampleInput));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(4_111L, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInputs() {
        assertEquals(3_993L, day.resolvePart2(sampleInput));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(4_917L, day.resolvePart2(dayInput));
    }
}