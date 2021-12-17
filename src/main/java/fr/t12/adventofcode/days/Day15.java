package fr.t12.adventofcode.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
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

    record PositionWithDistance(Position position, int distance) {
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

            int[][] distances = buildDistancesArray(increaseWidth, increaseHeight);
            distances[0][0] = 0;

            PriorityQueue<PositionWithDistance> positionsToCheck = new PriorityQueue<>(Comparator.comparingInt(PositionWithDistance::distance));
            positionsToCheck.add(new PositionWithDistance(start, 0));

            while (!positionsToCheck.isEmpty()) {
                Position position = positionsToCheck.poll().position;
                if (position.equals(end)) {
                    return distances[position.y][position.x];
                }
                for (Position neighbor : position.getNeighbors(increaseWidth, increaseHeight)) {
                    int newDistance = distances[position.y][position.x] + getRiskLevel(neighbor);
                    if (newDistance < distances[neighbor.y][neighbor.x]) {
                        distances[neighbor.y][neighbor.x] = newDistance;
                        positionsToCheck.add(new PositionWithDistance(neighbor, newDistance));
                    }
                }
            }

            throw new IllegalStateException("No solution found");
        }

        private int[][] buildDistancesArray(int width, int height) {
            int[][] distances = new int[height][];
            int y = 0;
            while (y < height) {
                distances[y] = new int[width];
                int x = 0;
                while (x < width) {
                    distances[y][x] = Integer.MAX_VALUE;
                    x++;
                }
                y++;
            }
            return distances;
        }

        private int getRiskLevel(Position position) {
            int deltaValue = position.x / this.width + position.y / this.height;
            int value = this.riskLevel[position.y % this.height][position.x % this.width] + deltaValue;
            return value > 9 ? value % 9 : value;
        }
    }
}
