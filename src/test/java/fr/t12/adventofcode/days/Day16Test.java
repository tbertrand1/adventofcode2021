package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test {

    private final Day16 day = new Day16();
    private final String dayInput = day.getInput();

    @Test
    public void testResolvePart1AndPart2WithSampleInputs() {
        assertEquals(16, day.resolvePart1AndPart2("8A004A801A8002F478").first());
        assertEquals(12, day.resolvePart1AndPart2("620080001611562C8802118E34").first());
        assertEquals(23, day.resolvePart1AndPart2("C0015000016115A2E0802F182340").first());
        assertEquals(31, day.resolvePart1AndPart2("A0016C880162017C3686B18A3D4780").first());
        assertEquals(3L, day.resolvePart1AndPart2("C200B40A82").second());
        assertEquals(54L, day.resolvePart1AndPart2("04005AC33890").second());
        assertEquals(7L, day.resolvePart1AndPart2("880086C3E88112").second());
        assertEquals(9L, day.resolvePart1AndPart2("CE00C43D881120").second());
        assertEquals(1L, day.resolvePart1AndPart2("D8005AC2A8F0").second());
        assertEquals(0L, day.resolvePart1AndPart2("F600BC2D8F").second());
        assertEquals(0L, day.resolvePart1AndPart2("9C005AC2F8F0").second());
        assertEquals(1L, day.resolvePart1AndPart2("9C0141080250320F1802104A08").second());
    }

    @Test
    public void testResolvePart1AndPart2WithDayInput() {
        assertEquals(Tuple.of(883, 1_675_198_555_015L), day.resolvePart1AndPart2(dayInput));
    }
}