package fr.t12.adventofcode.days;

import java.util.*;
import java.util.function.Function;

public class Day15 extends Day<List<String>, Integer, Integer> {

    public Day15() {
        super(15);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        Cave cave = new Cave(input);
        return cave.findLowestTotalRisk(1);
    }

    @Override
    protected Integer resolvePart2(List<String> input) {
        Cave cave = new Cave(input);
        return cave.findLowestTotalRisk(5);
    }

    record Position(int x, int y) {
        public List<Position> getNeighbors(int width, int height) {
            List<Position> neighbors = new ArrayList<>();
            if (this.x > 0) {
                neighbors.add(new Position(this.x - 1, this.y));
            }
            if (this.x < width - 1) {
                neighbors.add(new Position(this.x + 1, this.y));
            }
            if (this.y > 0) {
                neighbors.add(new Position(this.x, this.y - 1));
            }
            if (this.y < height - 1) {
                neighbors.add(new Position(this.x, this.y + 1));
            }
            return neighbors;
        }
    }

    record PositionComparator(Map<Position, Integer> cheapestPaths) implements Comparator<Position> {
        @Override
        public int compare(Position p1, Position p2) {
            int scoreP1 = this.cheapestPaths.get(p1);
            int scoreP2 = this.cheapestPaths.get(p2);
            return scoreP1 - scoreP2;
        }
    }

    static class Cave {
        private final int height;
        private final int width;
        private final int[][] riskLevel;

        protected Cave(List<String> lines) {
            this.height = lines.size();
            this.width = lines.get(0).length();

            this.riskLevel = new int[this.height][];
            int y = 0;
            while (y < this.height) {
                this.riskLevel[y] = new int[this.width];
                int x = 0;
                while (x < this.width) {
                    this.riskLevel[y][x] = Character.getNumericValue(lines.get(y).charAt(x));
                    x++;
                }
                y++;
            }
        }

        public int findLowestTotalRisk(int increaseMultiplier) {
            int increaseWidth = increaseMultiplier * this.width;
            int increaseHeight = increaseMultiplier * this.height;

            Position start = new Position(0, 0);
            Position end = new Position(increaseWidth - 1, increaseHeight - 1);

            Map<Position, Integer> cheapestPaths = new HashMap<>();
            cheapestPaths.put(start, 0);

            PriorityQueue<Position> positionsToCheck = new PriorityQueue<>(new PositionComparator(cheapestPaths));
            positionsToCheck.add(start);

            while (!positionsToCheck.isEmpty()) {
                Position position = positionsToCheck.poll();
                if (position.equals(end)) {
                    return cheapestPaths.get(position);
                }
                for (Position neighbor : position.getNeighbors(increaseWidth, increaseHeight)) {
                    int newPath = cheapestPaths.get(position) + getRiskLevel(neighbor);
                    if (newPath < cheapestPaths.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        cheapestPaths.put(neighbor, newPath);
                        if (!positionsToCheck.contains(neighbor)) {
                            positionsToCheck.add(neighbor);
                        }
                    }
                }
            }

            throw new IllegalStateException("No solution found");
        }

        private int getRiskLevel(Position position) {
            int deltaValue = position.x / this.width + position.y / this.height;
            int value = this.riskLevel[position.y % this.height][position.x % this.width] + deltaValue;
            return value > 9 ? value % 9 : value;
        }
    }
}
