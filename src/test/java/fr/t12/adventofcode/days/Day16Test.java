package fr.t12.adventofcode.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test {

    private final Day16 day = new Day16();
    private final String dayInput = day.getInput();

    @Test
    public void testResolvePart1WithSampleInputs() {
        assertEquals(16, day.resolvePart1("8A004A801A8002F478"));
        assertEquals(12, day.resolvePart1("620080001611562C8802118E34"));
        assertEquals(23, day.resolvePart1("C0015000016115A2E0802F182340"));
        assertEquals(31, day.resolvePart1("A0016C880162017C3686B18A3D4780"));
    }

    @Test
    public void testResolvePart1WithDayInput() {
        assertEquals(883, day.resolvePart1(dayInput));
    }

    @Test
    public void testResolvePart2WithSampleInputs() {
        assertEquals(3L, day.resolvePart2("C200B40A82"));
        assertEquals(54L, day.resolvePart2("04005AC33890"));
        assertEquals(7L, day.resolvePart2("880086C3E88112"));
        assertEquals(9L, day.resolvePart2("CE00C43D881120"));
        assertEquals(1L, day.resolvePart2("D8005AC2A8F0"));
        assertEquals(0L, day.resolvePart2("F600BC2D8F"));
        assertEquals(0L, day.resolvePart2("9C005AC2F8F0"));
        assertEquals(1L, day.resolvePart2("9C0141080250320F1802104A08"));
    }

    @Test
    public void testResolvePart2WithDayInput() {
        assertEquals(1_675_198_555_015L, day.resolvePart2(dayInput));
    }
}