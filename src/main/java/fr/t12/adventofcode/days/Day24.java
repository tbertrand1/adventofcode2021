package fr.t12.adventofcode.days;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.stream.IntStream;

public class Day24 extends Day<List<Day24.Instruction>, Long, Long> {

    public Day24() {
        super(24);
    }

    @Override
    protected List<Instruction> getInput() {
        return readInputAsItems(Instruction::parse);
    }

    @Override
    protected Long resolvePart1(List<Instruction> input) {
        List<Integer> digits = IntStream.range(1, 10).boxed().sorted(Comparator.reverseOrder()).toList();
        return findFirstValidMonad(input, digits, digits);
    }

    @Override
    protected Long resolvePart2(List<Instruction> input) {
        List<Integer> digits = IntStream.range(1, 10).boxed().toList();
        for (int firstDigit : digits) {
            try {
                return findFirstValidMonad(input, digits, List.of(firstDigit));
            } catch (Exception e) {
            }
        }
        throw new IllegalStateException("No solution found");
    }

    private Long findFirstValidMonad(List<Instruction> instructions, List<Integer> digits, List<Integer> firstDigits) {
        return findFirstValidAlu(instructions, digits, firstDigits, 0, Alu.initialAlu(), new HashSet<>())
                .map(alu -> Long.parseLong(alu.monad))
                .orElseThrow();
    }

    private Optional<Alu> findFirstValidAlu(List<Instruction> instructions, List<Integer> digits, List<Integer> firstDigits,
                                            int instructionIndex, Alu alu, Set<CacheKey> cache) {
        if (instructionIndex == instructions.size()) {
            return alu.isValid() ? Optional.of(alu) : Optional.empty();
        }

        Instruction instruction = instructions.get(instructionIndex);
        if (instruction.isInputInstruction()) {
            List<Alu> nextAlus = alu.determineNextAlus(digits, firstDigits, instruction);
            for (Alu nextAlu : nextAlus) {
                CacheKey cacheKey = CacheKey.createFromAlu(nextAlu, instructionIndex);
                if (!cache.contains(cacheKey)) {
                    Optional<Alu> validAlu = findFirstValidAlu(instructions, digits, firstDigits, instructionIndex + 1, nextAlu, cache);
                    cache.add(cacheKey);
                    if (validAlu.isPresent()) {
                        return validAlu;
                    }
                }
            }
            return Optional.empty();
        } else {
            alu.applyInstructionOnVariable(instruction);
            return findFirstValidAlu(instructions, digits, firstDigits, instructionIndex + 1, alu, cache);
        }
    }

    record Instruction(String action, String val1, String val2) {
        public static Instruction parse(String line) {
            String[] split = line.split(" ");
            return new Instruction(split[0], split[1], split.length == 3 ? split[2] : null);
        }

        public boolean isInputInstruction() {
            return "inp".equals(this.action);
        }
    }

    record Alu(int[] variables, String monad) {
        public static Alu initialAlu() {
            return new Alu(new int[4], "");
        }

        private static int getVariableIndex(String variable) {
            return switch (variable) {
                case "w" -> 0;
                case "x" -> 1;
                case "y" -> 2;
                case "z" -> 3;
                default -> throw new IllegalArgumentException(String.format("Invalid variable: %s", variable));
            };
        }

        public boolean isValid() {
            return this.variables[getVariableIndex("z")] == 0;
        }

        public List<Alu> determineNextAlus(List<Integer> digits, List<Integer> firstDigits, Instruction instruction) {
            List<Alu> nextAlus = new ArrayList<>();
            List<Integer> authorizedDigits = this.monad.length() == 0 ? firstDigits : digits;
            for (Integer digit : authorizedDigits) {
                int[] nextVariables = this.variables.clone();
                nextVariables[getVariableIndex(instruction.val1)] = digit;
                nextAlus.add(new Alu(nextVariables, String.format("%s%d", this.monad, digit)));
            }
            return nextAlus;
        }

        public void applyInstructionOnVariable(Instruction instruction) {
            int val1 = getValue(instruction.val1);
            int val2 = getValue(instruction.val2);
            switch (instruction.action) {
                case "add" -> setValue(instruction.val1, val1 + val2);
                case "mul" -> setValue(instruction.val1, val1 * val2);
                case "div" -> setValue(instruction.val1, val1 / val2);
                case "mod" -> setValue(instruction.val1, val1 % val2);
                case "eql" -> setValue(instruction.val1, (val1 == val2) ? 1 : 0);
            }
        }

        private int getValue(String variableOrValue) {
            if (NumberUtils.isParsable(variableOrValue)) {
                return Integer.parseInt(variableOrValue);
            }
            return this.variables[getVariableIndex(variableOrValue)];
        }

        private void setValue(String variable, int value) {
            this.variables[getVariableIndex(variable)] = value;
        }
    }

    record CacheKey(int w, int z, int instructionIndex) {
        public static CacheKey createFromAlu(Alu alu, int instructionIndex) {
            return new CacheKey(
                    alu.variables[0],
                    alu.variables[3],
                    instructionIndex
            );
        }
    }
}
