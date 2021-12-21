package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day14 extends Day<List<String>, Long, Long> {
	private static final int NB_STEPS_PART_1 = 10;
	private static final int NB_STEPS_PART_2 = 40;

	public Day14() {
		super(14);
	}

	@Override
	protected List<String> getInput() {
		return readInputAsItems(Function.identity());
	}

	@Override
	protected Tuple<Long, Long> resolvePart1AndPart2(List<String> input) {
		String template = input.get(0);
		Map<String, Long> pairCounts = new HashMap<>();
		Map<String, Long> elementCounts = new HashMap<>();
		for (int i = 0; i < template.length(); i++) {
			if (i < template.length() - 1) {
				pairCounts.merge(template.substring(i, i + 2), 1L, Long::sum);
			}
			elementCounts.merge(template.substring(i, i + 1), 1L, Long::sum);
		}

		Map<String, String> elementInsertions = new HashMap<>();
		for (int i = 2; i < input.size(); i++) {
			String[] split = input.get(i).split(" -> ");
			elementInsertions.put(split[0], split[1]);
		}

		long resultPart1 = 0L;
		int step = 0;
		while (step < NB_STEPS_PART_2) {
			Map<String, Long> newPairCounts = new HashMap<>();
			for (String pair : pairCounts.keySet()) {
				if (elementInsertions.containsKey(pair)) {
					String newElement = elementInsertions.get(pair);
					Long nbPairs = pairCounts.get(pair);
					newPairCounts.merge(pair.charAt(0) + newElement, nbPairs, Long::sum);
					newPairCounts.merge(newElement + pair.charAt(1), nbPairs, Long::sum);
					elementCounts.merge(newElement, nbPairs, Long::sum);
				}
			}
			pairCounts = newPairCounts;
			step++;
			if (step == NB_STEPS_PART_1) {
				resultPart1 = calculateResult(elementCounts);
			}
		}

		long resultPart2 = calculateResult(elementCounts);
		return Tuple.of(resultPart1, resultPart2);
	}

	private long calculateResult(Map<String, Long> elementCounts) {
		long min = elementCounts.values().stream().reduce(Long::min).orElseThrow();
		long max = elementCounts.values().stream().reduce(Long::max).orElseThrow();
		return max - min;
	}
}
