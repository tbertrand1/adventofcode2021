package fr.t12.adventofcode.days;

import java.util.List;
import java.util.function.Function;

public class Day25 extends Day<List<String>, Integer, Void> {

    public Day25() {
        super(25);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        Grid grid = Grid.initializeGrid(input);

        int step = 0;
        boolean hasMoved = true;
        while (hasMoved) {
            step++;
            hasMoved = grid.moveEast();
            hasMoved |= grid.moveSouth();
        }

        return step;
    }

    @Override
    protected String formatPart2Result(Void result2) {
        return "NA";
    }

    static final class Grid {
        private static final char EMPTY = '.';
        private static final char EAST = '>';
        private static final char SOUTH = 'v';

        private final int height;
        private final int width;
        private char[][] grid;

        Grid(char[][] grid, int height, int width) {
            this.grid = grid;
            this.height = height;
            this.width = width;
        }

        public static Grid initializeGrid(List<String> input) {
            int height = input.size();
            int width = input.get(0).length();
            char[][] grid = new char[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    grid[y][x] = input.get(y).charAt(x);
                }
            }
            return new Grid(grid, height, width);
        }

        public boolean moveEast() {
            boolean hasMoved = false;
            char[][] nextGrid = cloneGrid(grid);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (grid[y][x] == EAST && grid[y][(x + 1) % width] == EMPTY) {
                        nextGrid[y][x] = EMPTY;
                        nextGrid[y][(x + 1) % width] = EAST;
                        hasMoved = true;
                    }
                }
            }
            this.grid = nextGrid;
            return hasMoved;
        }

        public boolean moveSouth() {
            boolean hasMoved = false;
            char[][] nextGrid = cloneGrid(grid);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (grid[y][x] == SOUTH && grid[(y + 1) % height][x] == EMPTY) {
                        nextGrid[y][x] = EMPTY;
                        nextGrid[(y + 1) % height][x] = SOUTH;
                        hasMoved = true;
                    }
                }
            }
            this.grid = nextGrid;
            return hasMoved;
        }

        private char[][] cloneGrid(char[][] grid) {
            char[][] cloned = grid.clone();
            for (int y = 0; y < grid.length; y++) {
                cloned[y] = grid[y].clone();
            }
            return cloned;
        }
    }
}
