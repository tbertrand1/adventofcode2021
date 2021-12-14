package fr.t12.adventofcode.days;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day14 extends Day<List<String>, Long, Long> {

	public Day14() {
		super(14);
	}

	@Override
	protected List<String> getInput() {
		return readInputAsItems(Function.identity());
	}

	@Override
	protected Long resolvePart1(List<String> input) {
		return resolve(input, 10);
	}

	@Override
	protected Long resolvePart2(List<String> input) {
		return resolve(input, 40);
	}

	private Long resolve(List<String> input, int nbSteps) {
		String template = input.get(0);
		Map<String, Long> pairCounts = new HashMap<>();
		Map<String, Long> elementCounts = new HashMap<>();
		for (int i = 0; i < template.length(); i++) {
			if (i < template.length() - 1) {
				incrementValue(pairCounts, template.substring(i, i + 2));
			}
			incrementValue(elementCounts, template.substring(i, i + 1));
		}

		Map<String, String> elementInsertions = new HashMap<>();
		for (int i = 2; i < input.size(); i++) {
			String[] split = input.get(i).split(" -> ");
			elementInsertions.put(split[0], split[1]);
		}

		int step = 0;
		while (step < nbSteps) {
			Map<String, Long> newPairCounts = new HashMap<>();
			for (String pair : pairCounts.keySet()) {
				if (elementInsertions.containsKey(pair)) {
					String newElement = elementInsertions.get(pair);
					Long nbPairs = pairCounts.get(pair);
					incrementValue(newPairCounts, pair.charAt(0) + newElement, nbPairs);
					incrementValue(newPairCounts, newElement + pair.charAt(1), nbPairs);
					incrementValue(elementCounts, newElement, nbPairs);
				}
			}
			pairCounts = newPairCounts;
			step++;
		}

		List<Long> elementCountsSorted = elementCounts.values()
				.stream()
				.sorted()
				.toList();
		return elementCountsSorted.get(elementCountsSorted.size() - 1) - elementCountsSorted.get(0);
	}

	private static void incrementValue(Map<String, Long> map, String key, Long value) {
		map.put(key, map.getOrDefault(key, 0L) + value);
	}

	private static void incrementValue(Map<String, Long> map, String key) {
		incrementValue(map, key, 1L);
	}
}
