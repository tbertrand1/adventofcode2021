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
				pairCounts.merge(template.substring(i, i + 2), 1L, Long::sum);
			}
			elementCounts.merge(template.substring(i, i + 1), 1L, Long::sum);
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
					newPairCounts.merge(pair.charAt(0) + newElement, nbPairs, Long::sum);
					newPairCounts.merge(newElement + pair.charAt(1), nbPairs, Long::sum);
					elementCounts.merge(newElement, nbPairs, Long::sum);
				}
			}
			pairCounts = newPairCounts;
			step++;
		}

		long min = elementCounts.values().stream().reduce(Long::min).orElseThrow();
		long max = elementCounts.values().stream().reduce(Long::max).orElseThrow();
		return max - min;
	}
}
