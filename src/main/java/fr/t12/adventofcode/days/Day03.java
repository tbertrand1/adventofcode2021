package fr.t12.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day03 extends Day<List<String>, Integer, Integer> {

    public Day03() {
        super(3);
    }

    private static String findRating(List<String> input, char bitIfMoreZeros, char bitIfMoreOnes) {
        int diagLength = input.get(0).length();

        List<String> remainingDiags = new ArrayList<>(input);
        for (int i = 0; i < diagLength; i++) {
            final int charIndex = i;
            long nbOnes = remainingDiags.stream().filter(diag -> diag.charAt(charIndex) == '1').count();
            final char mostCommonBit = (nbOnes >= (remainingDiags.size() - nbOnes)) ? bitIfMoreOnes : bitIfMoreZeros;

            List<String> nextRemainingDiags = remainingDiags.stream()
                    .filter(diag -> diag.charAt(charIndex) == mostCommonBit)
                    .collect(Collectors.toList());
            if (nextRemainingDiags.size() == 1) {
                return nextRemainingDiags.get(0);
            }
            remainingDiags = nextRemainingDiags;
        }

        throw new IllegalStateException("No solution found");
    }

    private static Integer convertBinaryToInteger(String binary) {
        return Integer.parseInt(binary, 2);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        int diagLength = input.get(0).length();
        int nbDiags = input.size();

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (int i = 0; i < diagLength; i++) {
            final int charIndex = i;
            long nbOnes = input.stream().filter(diag -> diag.charAt(charIndex) == '1').count();
            if (nbOnes > nbDiags / 2) {
                gamma.append("1");
                epsilon.append("0");
            } else {
                gamma.append("0");
                epsilon.append("1");
            }
        }

        return convertBinaryToInteger(gamma.toString()) * convertBinaryToInteger(epsilon.toString());
    }

    @Override
    protected Integer resolvePart2(List<String> input) {
        String oxygenRating = findRating(input, '0', '1');
        String co2Rating = findRating(input, '1', '0');
        return convertBinaryToInteger(oxygenRating) * convertBinaryToInteger(co2Rating);
    }
}
