package fr.t12.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Day18 extends Day<List<String>, Long, Long> {

    public Day18() {
        super(18);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Long resolvePart1(List<String> input) {
        return input.stream()
                .map(SnailfishNumber::parse)
                .reduce(SnailfishNumber::sum)
                .map(SnailfishNumber::determineMagnitude)
                .orElseThrow();
    }

    @Override
    protected Long resolvePart2(List<String> input) {
        List<SnailfishNumber> snailfishNumbers = input.stream()
                .map(SnailfishNumber::parse)
                .toList();
        long maxMagnitude = 0L;
        for (SnailfishNumber number1 : snailfishNumbers) {
            for (SnailfishNumber number2 : snailfishNumbers) {
                if (number1.equals(number2)) {
                    continue;
                }
                SnailfishNumber sum = number1.clone().sum(number2.clone());
                maxMagnitude = Math.max(maxMagnitude, sum.determineMagnitude());
            }
        }
        return maxMagnitude;
    }

    static class SnailfishNumber {
        private SnailfishNumber left;
        private SnailfishNumber right;
        private Long value;
        private SnailfishNumber parent;

        private SnailfishNumber(SnailfishNumber left, SnailfishNumber right) {
            this.left = left;
            this.right = right;
            left.parent = this;
            right.parent = this;
        }

        private SnailfishNumber(Long value) {
            this.value = value;
        }

        public static SnailfishNumber parse(String line) {
            if (line.length() == 1) {
                return new SnailfishNumber(Long.parseLong(line));
            }
            int level = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '[') {
                    level++;
                } else if (line.charAt(i) == ']') {
                    level--;
                } else if (line.charAt(i) == ',' && level == 1) {
                    return new SnailfishNumber(
                            SnailfishNumber.parse(line.substring(1, i)),
                            SnailfishNumber.parse(line.substring(i + 1, line.length() - 1))
                    );
                }
            }
            throw new IllegalStateException("Illegal line: " + line);
        }

        @Override
        public SnailfishNumber clone() {
            if (hasValue()) {
                return new SnailfishNumber(this.value);
            }
            return new SnailfishNumber(this.left.clone(), this.right.clone());
        }

        public SnailfishNumber sum(SnailfishNumber snailfishNumber) {
            SnailfishNumber sum = new SnailfishNumber(this, snailfishNumber);
            return sum.reduce();
        }

        protected SnailfishNumber reduce() {
            while (true) {
                boolean changed = false;
                while (explode()) {
                    changed = true;
                }
                if (split()) {
                    changed = true;
                }
                if (!changed) {
                    return this;
                }
            }
        }

        private boolean explode() {
            if (!hasValue()) {
                if (getLevel() == 4) {
                    List<SnailfishNumber> numbersWithValue = getRootParent().getAllNumbersWithValue();
                    int nearestLeftIndex = numbersWithValue.indexOf(this.left) - 1;
                    if (nearestLeftIndex >= 0) {
                        numbersWithValue.get(nearestLeftIndex).value += this.left.value;
                    }
                    int nearestRightIndex = numbersWithValue.indexOf(this.right) + 1;
                    if (nearestRightIndex < numbersWithValue.size()) {
                        numbersWithValue.get(nearestRightIndex).value += this.right.value;
                    }
                    this.left = null;
                    this.right = null;
                    this.value = 0L;
                    return true;
                } else {
                    return this.left.explode() || this.right.explode();
                }
            }
            return false;
        }

        private boolean split() {
            if (hasValue()) {
                if (this.value > 9) {
                    long splittedValue = this.value / 2;
                    this.left = new SnailfishNumber(splittedValue);
                    this.right = new SnailfishNumber(splittedValue + this.value % 2);
                    this.left.parent = this;
                    this.right.parent = this;
                    this.value = null;
                    return true;
                }
            } else {
                return this.left.split() || this.right.split();
            }
            return false;
        }

        private boolean hasValue() {
            return this.value != null;
        }

        private int getLevel() {
            return this.parent == null ? 0 : this.parent.getLevel() + 1;
        }

        private SnailfishNumber getRootParent() {
            SnailfishNumber curr = this;
            while (curr.parent != null) {
                curr = curr.parent;
            }
            return curr;
        }

        private List<SnailfishNumber> getAllNumbersWithValue() {
            if (hasValue()) {
                return List.of(this);
            }
            List<SnailfishNumber> allWithValue = new ArrayList<>();
            allWithValue.addAll(this.left.getAllNumbersWithValue());
            allWithValue.addAll(this.right.getAllNumbersWithValue());
            return allWithValue;
        }

        public long determineMagnitude() {
            if (hasValue()) {
                return this.value;
            }
            return 3 * this.left.determineMagnitude() + 2 * this.right.determineMagnitude();
        }

        @Override
        public String toString() {
            if (hasValue()) {
                return String.valueOf(this.value);
            }
            return String.format("[%s,%s]", this.left, this.right);
        }
    }
}
