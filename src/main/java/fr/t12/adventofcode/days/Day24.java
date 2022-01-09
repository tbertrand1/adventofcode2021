package fr.t12.adventofcode.days;

import java.util.*;
import java.util.stream.IntStream;

public class Day24 extends Day<Void, Long, Long> {

    public Day24() {
        super(24);
    }

    @Override
    protected Void getInput() {
        return null;
    }

    @Override
    protected Long resolvePart1(Void input) {
        List<Integer> digits = IntStream.range(1, 10).boxed().sorted(Comparator.reverseOrder()).toList();
        return findFirstValidMonad(digits);
    }

    @Override
    protected Long resolvePart2(Void input) {
        List<Integer> digits = IntStream.range(1, 10).boxed().toList();
        return findFirstValidMonad(digits);
    }

    private Long findFirstValidMonad(List<Integer> digits) {
        return findFirstValidAlu(digits, Program.initialProgram(), new HashSet<>())
                .map(p -> Long.parseLong(p.monad))
                .orElseThrow();
    }

    private Optional<Program> findFirstValidAlu(List<Integer> digits, Program program, Set<Program> cache) {
        if (program.step == Program.NB_STEPS) {
            return program.isValid() ? Optional.of(program) : Optional.empty();
        }

        List<Program> nextPrograms = program.determineNextStepProgram(digits);
        for (Program nextProgram : nextPrograms) {
            if (!cache.contains(nextProgram)) {
                Optional<Program> validProgram = findFirstValidAlu(digits, nextProgram, cache);
                cache.add(nextProgram);
                if (validProgram.isPresent()) {
                    return validProgram;
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Represents the input rewrited as a java class.
     * Input is a list of 14 blocks of 18 lines with specifics values at lines 5, 6, and 16.
     */
    static final class Program {
        private static final int NB_STEPS = 14;

        private static final int[] VARIABLE_LINE_5 = new int[]{
                1, 1, 1, 1, 26, 1, 1, 26, 1, 26, 26, 26, 26, 26
        };
        private static final int[] VARIABLE_LINE_6 = new int[]{
                12, 11, 10, 10, -16, 14, 12, -4, 15, -7, -8, -4, -15, -8
        };
        private static final int[] VARIABLE_LINE_16 = new int[]{
                6, 12, 5, 10, 7, 0, 4, 12, 14, 13, 10, 11, 9, 9
        };
        private int z;
        private int step;
        private String monad;

        public Program(int z, int step, String monad) {
            this.z = z;
            this.step = step;
            this.monad = monad;
        }

        public static Program initialProgram() {
            return new Program(0, 0, "");
        }

        private void execute(int w) {
            int x = this.z % 26;
            this.z /= VARIABLE_LINE_5[step];
            x += VARIABLE_LINE_6[step];
            x = (x == w) ? 0 : 1;
            int y = 25 * x + 1;
            this.z *= y;
            y = (w + VARIABLE_LINE_16[step]) * x;
            this.z += y;

            this.monad = this.monad.concat(Integer.toString(w));
            this.step++;
        }

        public List<Program> determineNextStepProgram(List<Integer> digits) {
            List<Program> nextSteps = new ArrayList<>();
            for (Integer digit : digits) {
                Program nextProgram = this.clone();
                nextProgram.execute(digit);
                nextSteps.add(nextProgram);
            }
            return nextSteps;
        }

        public boolean isValid() {
            return this.z == 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Program program = (Program) o;
            return this.z == program.z && this.step == program.step;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.z, this.step);
        }

        @Override
        protected Program clone() {
            return new Program(this.z, this.step, this.monad);
        }
    }
}
