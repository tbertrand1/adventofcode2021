package fr.t12.adventofcode.days;

import java.util.*;
import java.util.stream.Collectors;

public class Day08 extends Day<List<Day08.Entry>, Integer, Integer> {

    public Day08() {
        super(8);
    }

    @Override
    protected List<Entry> getInput() {
        return readInputAsItems(Day08.Entry::parse);
    }

    @Override
    protected Integer resolvePart1(List<Entry> input) {
        return input.stream().mapToInt(Entry::countOutputsUniquesDigits).sum();
    }

    @Override
    protected Integer resolvePart2(List<Entry> input) {
        return input.stream().mapToInt(Entry::determineOutputValue).sum();
    }

    record Entry(List<String> inputs, List<String> outputs) {

        private static final Set<Integer> UNIQUES_DIGITS_LENGTHS = Set.of(2, 3, 4, 7);

        public static Entry parse(String value) {
            String[] split = value.split(" \\| ");
            return new Entry(extractDigits(split[0]), extractDigits(split[1]));
        }

        private static List<String> extractDigits(String value) {
            return Arrays.stream(value.split(" ")).map(Entry::reorderSegments).toList();
        }

        private static String reorderSegments(String value) {
            return extractSegments(value).stream().sorted().map(Object::toString).collect(Collectors.joining());
        }

        private static List<Character> extractSegments(String value) {
            return value.chars().mapToObj(e -> (char) e).toList();
        }

        private static int countCommonSegments(String digit1, String digit2) {
            List<Character> segments1 = extractSegments(digit1);
            List<Character> segments2 = extractSegments(digit2);
            return (int) segments1.stream().filter(segments2::contains).count();
        }

        public int countOutputsUniquesDigits() {
            return (int) outputs.stream().filter(o -> UNIQUES_DIGITS_LENGTHS.contains(o.length())).count();
        }

        public int determineOutputValue() {
            Map<Integer, List<String>> digitsByNbSegments = inputs.stream().collect(Collectors.groupingBy(String::length));

            Map<Integer, String> digitByValue = new HashMap<>();

            // Digit 1 is the only one with 2 segments
            digitByValue.put(1, digitsByNbSegments.get(2).get(0));
            // Digit 4 is the only one with 4 segments
            digitByValue.put(4, digitsByNbSegments.get(4).get(0));
            // Digit 7 is the only one with 3 segments
            digitByValue.put(7, digitsByNbSegments.get(3).get(0));
            // Digit 8 is the only one with 7 segments
            digitByValue.put(8, digitsByNbSegments.get(7).get(0));

            // Digits 2, 3 and 5: 5 segments
            for (String digit : digitsByNbSegments.get(5)) {
                if (countCommonSegments(digit, digitByValue.get(1)) == 2) {
                    // Digit 3 contains 2 segments of digit 1
                    digitByValue.put(3, digit);
                } else if (countCommonSegments(digit, digitByValue.get(4)) == 2) {
                    // Digit 2 contains 2 segments of digit 4
                    digitByValue.put(2, digit);
                } else {
                    digitByValue.put(5, digit);
                }
            }

            // Digits 0, 6 and 9: 6 segments
            for (String digit : digitsByNbSegments.get(6)) {
                if (countCommonSegments(digit, digitByValue.get(1)) == 1) {
                    // Digit 6 contains 1 segment of digit 1
                    digitByValue.put(6, digit);
                } else if (countCommonSegments(digit, digitByValue.get(4)) == 4) {
                    // Digit 9 contains 4 segments of digit 4
                    digitByValue.put(9, digit);
                } else {
                    digitByValue.put(0, digit);
                }
            }

            if (digitByValue.size() != 10) {
                throw new IllegalStateException("Digits missing");
            }
            Map<String, Integer> digitsValues = digitByValue.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            String output = outputs.stream().map(digit -> digitsValues.get(digit).toString()).collect(Collectors.joining());
            return Integer.parseInt(output);
        }
    }
}
