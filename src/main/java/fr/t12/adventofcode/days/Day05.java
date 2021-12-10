package fr.t12.adventofcode.days;

import java.util.List;

public class Day05 extends Day<List<Day05.Line>, Long, Long> {

    private static final int SIZE = 1_000;

    public Day05() {
        super(5);
    }

    @Override
    protected List<Line> getInput() {
        return readInputAsItems(Day05.Line::parse);
    }

    @Override
    protected Long resolvePart1(List<Line> input) {
        long[][] cells = new long[SIZE][SIZE];
        input.stream().filter(Line::isHorizontalOrVertical).forEach(line -> line.completeMap(cells));
        return countNbCellsWithValueGreaterThan1(cells);
    }

    @Override
    protected Long resolvePart2(List<Line> input) {
        long[][] cells = new long[SIZE][SIZE];
        input.forEach(line -> line.completeMap(cells));
        return countNbCellsWithValueGreaterThan1(cells);
    }

    private long countNbCellsWithValueGreaterThan1(long[][] cells) {
        long count = 0;
        for (long[] row : cells) {
            for (long value : row) {
                if (value > 1) {
                    count++;
                }
            }
        }
        return count;
    }

    record Coordinate(int x, int y) {
        public static Coordinate parse(String value) {
            String[] split = value.split(",");
            return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }

    record Line(Coordinate c1, Coordinate c2) {
        public static Line parse(String inputLine) {
            String[] split = inputLine.split(" -> ");
            return new Line(Coordinate.parse(split[0]), Coordinate.parse(split[1]));
        }

        public boolean isHorizontalOrVertical() {
            return c1.x == c2.x || c1.y == c2.y;
        }

        public void completeMap(long[][] cells) {
            int deltaX = Integer.compare(c2.x, c1.x);
            int deltaY = Integer.compare(c2.y, c1.y);
            int currentX = c1.x;
            int currentY = c1.y;
            while (currentX != c2.x + deltaX || currentY != c2.y + deltaY) {
                cells[currentX][currentY] += 1;
                currentX += deltaX;
                currentY += deltaY;
            }
        }
    }
}
