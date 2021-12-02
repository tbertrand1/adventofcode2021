package fr.t12.adventofcode.days;

import java.util.List;

public class Day02 extends Day<List<Day02.Action>, Integer, Integer> {

    public Day02() {
        super(2);
    }

    @Override
    protected List<Action> getInput() {
        return readInputAsItems(Action::parse);
    }

    @Override
    protected Integer resolvePart1(List<Action> input) {
        int horizontalPosition = 0;
        int depth = 0;
        for (Action action : input) {
            switch (action.direction) {
                case FORWARD -> horizontalPosition += action.value;
                case DOWN -> depth += action.value;
                case UP -> depth -= action.value;
            }
        }
        return horizontalPosition * depth;
    }

    @Override
    protected Integer resolvePart2(List<Action> input) {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;
        for (Action action : input) {
            switch (action.direction) {
                case FORWARD -> {
                    horizontalPosition += action.value;
                    depth += aim * action.value;
                }
                case DOWN -> aim += action.value;
                case UP -> aim -= action.value;
            }
        }
        return horizontalPosition * depth;
    }

    protected enum Direction {
        FORWARD, DOWN, UP
    }

    protected record Action(Direction direction, Integer value) {
        public static Action parse(String line) {
            String[] split = line.split(" ");
            return new Action(
                    Direction.valueOf(split[0].toUpperCase()),
                    Integer.valueOf(split[1])
            );
        }
    }
}
