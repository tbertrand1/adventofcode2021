package fr.t12.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day05 extends Day<List<Day05.Line>, Long, Long> {

    public Day05() {
        super(5);
    }

    @Override
    protected List<Line> getInput() {
        return readInputAsItems(Day05.Line::parse);
    }

    @Override
    protected Long resolvePart1(List<Line> input) {
        return input.stream()
                .filter(Line::isHorizontalOrVertical)
                .map(Line::generateAllCoordinates)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .filter(v -> v >= 2)
                .count();
    }

    @Override
    protected Long resolvePart2(List<Line> input) {
        return input.stream()
                .map(Line::generateAllCoordinates)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .filter(v -> v >= 2)
                .count();
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

        public List<Coordinate> generateAllCoordinates() {
            List<Coordinate> coordinates = new ArrayList<>();
            coordinates.add(c1);

            int deltaX = Integer.compare(c2.x, c1.x);
            int deltaY = Integer.compare(c2.y, c1.y);
            int currentX = c1.x;
            int currentY = c1.y;
            while (currentX != c2.x || currentY != c2.y) {
                currentX += deltaX;
                currentY += deltaY;
                coordinates.add(new Coordinate(currentX, currentY));
            }

            return coordinates;
        }
    }
}
