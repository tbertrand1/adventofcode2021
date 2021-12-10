package fr.t12.adventofcode.days;

import java.util.*;
import java.util.function.Function;

public class Day10 extends Day<List<String>, Long, Long> {

    private static final Map<Character, Character> CHARACTERS = Map.of(
            ')', '(',
            ']', '[',
            '}', '{',
            '>', '<'
    );
    private static final Map<Character, Integer> ERRORS_VALUES = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137
    );
    private static final Map<Character, Integer> INCOMPLETES_VALUES = Map.of(
            '(', 1,
            '[', 2,
            '{', 3,
            '<', 4
    );

    public Day10() {
        super(10);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Long resolvePart1(List<String> input) {
        long errorScore = 0;
        for (String line : input) {
            Stack<Character> stack = new Stack<>();
            for (Character c : line.toCharArray()) {
                if (CHARACTERS.containsValue(c)) {
                    stack.push(c);
                } else {
                    if (stack.peek() == CHARACTERS.get(c)) {
                        stack.pop();
                    } else {
                        errorScore += ERRORS_VALUES.get(c);
                        break;
                    }
                }
            }
        }
        return errorScore;
    }

    @Override
    protected Long resolvePart2(List<String> input) {
        List<Long> incompleteScores = new ArrayList<>();
        for (String line : input) {
            Stack<Character> stack = new Stack<>();
            boolean hasError = false;
            for (Character c : line.toCharArray()) {
                if (CHARACTERS.containsValue(c)) {
                    stack.push(c);
                } else {
                    if (stack.peek() == CHARACTERS.get(c)) {
                        stack.pop();
                    } else {
                        hasError = true;
                        break;
                    }
                }
            }
            if (!hasError) {
                long incompleteScore = 0;
                while (stack.size() > 0) {
                    Character c = stack.pop();
                    incompleteScore = incompleteScore * 5 + INCOMPLETES_VALUES.get(c);
                }
                incompleteScores.add(incompleteScore);
            }
        }
        Collections.sort(incompleteScores);
        return incompleteScores.get(incompleteScores.size() / 2);
    }
}
