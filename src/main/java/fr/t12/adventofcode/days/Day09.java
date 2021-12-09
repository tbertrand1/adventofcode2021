package fr.t12.adventofcode.days;

import java.util.*;
import java.util.function.Function;

public class Day09 extends Day<Day09.Grid, Integer, Integer> {

    public Day09() {
        super(9);
    }

    @Override
    protected Grid getInput() {
        return new Grid(readInputAsItems(Function.identity()));
    }

    @Override
    protected Integer resolvePart1(Grid grid) {
        return grid.findLowestPoints().stream().mapToInt(p -> p.getValue() + 1).sum();
    }

    @Override
    protected Integer resolvePart2(Grid grid) {
        return grid.findBasins().stream()
                .map(Collection::size)
                .sorted(Collections.reverseOrder())
                .limit(3)
                .mapToInt(Integer::intValue)
                .reduce(1, (s1, s2) -> s1 * s2);
    }

    static class Grid {
        private final int[][] map;
        private final int width;
        private final int height;

        public Grid(List<String> lines) {
            this.width = lines.size();
            this.height = lines.get(0).length();
            this.map = new int[width][];
            for (int i = 0; i < width; i++) {
                this.map[i] = Arrays.stream(lines.get(i).split("")).mapToInt(Integer::parseInt).toArray();
            }
        }

        public List<Point> findLowestPoints() {
            List<Point> lowestPoints = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Point p = new Point(this, i, j);
                    if (p.findNeighbors().stream().allMatch(n -> p.getValue() < n.getValue())) {
                        lowestPoints.add(p);
                    }
                }
            }
            return lowestPoints;
        }

        public List<Set<Point>> findBasins() {
            return findLowestPoints().stream().map(point -> {
                Set<Point> basin = new HashSet<>();
                basin.add(point);
                completeBasin(point, basin);
                return basin;
            }).toList();
        }

        private void completeBasin(Point point, Set<Point> basin) {
            point.findNeighbors().stream()
                    .filter(n -> !basin.contains(n) && n.getValue() != 9)
                    .forEach(n -> {
                        basin.add(n);
                        completeBasin(n, basin);
                    });
        }
    }

    record Point(Grid grid, int i, int j) {
        public int getValue() {
            return grid.map[i][j];
        }

        public List<Point> findNeighbors() {
            List<Point> neighbors = new ArrayList<>();
            if (i > 0) {
                neighbors.add(new Point(grid, i - 1, j));
            }
            if (i < grid.width - 1) {
                neighbors.add(new Point(grid, i + 1, j));
            }
            if (j > 0) {
                neighbors.add(new Point(grid, i, j - 1));
            }
            if (j < grid.height - 1) {
                neighbors.add(new Point(grid, i, j + 1));
            }
            return neighbors;
        }
    }
}
