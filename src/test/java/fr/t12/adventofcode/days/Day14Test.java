package fr.t12.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class Day14Test {

	private final Day14 day = new Day14();
	private final List<String> sampleInput = List.of(
			"NNCB",
			"",
			"CH -> B",
			"HH -> N",
			"CB -> H",
			"NH -> C",
			"HB -> C",
			"HC -> B",
			"HN -> C",
			"NN -> C",
			"BH -> H",
			"NC -> B",
			"NB -> B",
			"BN -> B",
			"BB -> N",
			"BC -> B",
			"CC -> N",
			"CN -> C"
	);
	private final List<String> dayInput = day.getInput();

	@Test
	public void testResolvePart1WithSampleInput() {
		assertEquals(1_588L, day.resolvePart1(sampleInput));
	}

	@Test
	public void testResolvePart1WithDayInput() {
		assertEquals(3_284L, day.resolvePart1(dayInput));
	}

	@Test
	public void testResolvePart2WithSampleInput() {
		assertEquals(2_188_189_693_529L, day.resolvePart2(sampleInput));
	}

	@Test
	public void testResolvePart2WithDayInput() {
		assertEquals(4_302_675_529_689L, day.resolvePart2(dayInput));
	}
}