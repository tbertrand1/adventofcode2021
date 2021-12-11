package fr.t12.adventofcode.days;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day11 extends Day<List<String>, Integer, Integer> {

    private static final int SIZE = 10;
    private static final int NB_STEPS = 100;

    public Day11() {
        super(11);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        List<Cell> cells = initializeCells(input);
        int step = 0;
        int nbFlashes = 0;
        while (step < NB_STEPS) {
            step++;
            Set<Cell> cellsFlashes = new HashSet<>();
            cells.forEach(cell -> cell.incrementValueAndFlash(cellsFlashes));
            nbFlashes += cellsFlashes.size();
        }
        return nbFlashes;
    }

    @Override
    protected Integer resolvePart2(List<String> input) {
        List<Cell> cells = initializeCells(input);
        int step = 0;
        while (true) {
            step++;
            Set<Cell> cellsFlashes = new HashSet<>();
            cells.forEach(cell -> cell.incrementValueAndFlash(cellsFlashes));
            if (cellsFlashes.size() == cells.size()) {
                return step;
            }
        }
    }

    private List<Cell> initializeCells(List<String> input) {
        List<Cell> cells = new ArrayList<>();
        int y = 0;
        for (String line : input) {
            int x = 0;
            for (Character c : line.toCharArray()) {
                cells.add(new Cell(x, y, Integer.parseInt(c.toString())));
                x++;
            }
            y++;
        }
        cells.forEach(cell -> cell.determineNeighbors(cells));
        return cells;
    }

    static class Cell {
        private final int x;
        private final int y;
        private final List<Cell> neighbors = new ArrayList<>();
        private int value;

        public Cell(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public void determineNeighbors(List<Cell> cells) {
            for (int deltaX = this.x - 1; deltaX <= this.x + 1; deltaX++) {
                for (int deltaY = this.y - 1; deltaY <= this.y + 1; deltaY++) {
                    if ((deltaX == this.x && deltaY == this.y) ||
                            deltaX < 0 || deltaX == SIZE ||
                            deltaY < 0 || deltaY == SIZE) {
                        continue;
                    }
                    int finalDeltaX = deltaX;
                    int finalDeltaY = deltaY;
                    this.neighbors.add(
                            cells.stream()
                                    .filter(c -> c.x == finalDeltaX && c.y == finalDeltaY)
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalStateException("No cell found"))
                    );
                }
            }
        }

        public void incrementValueAndFlash(Set<Cell> cellsFlashes) {
            if (!cellsFlashes.contains(this)) {
                value++;
                if (value > 9) {
                    value = 0;
                    cellsFlashes.add(this);
                    neighbors.forEach(c -> c.incrementValueAndFlash(cellsFlashes));
                }
            }
        }
    }
}
