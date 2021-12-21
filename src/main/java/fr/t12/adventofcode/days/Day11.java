package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.GifSequenceWriter;
import fr.t12.adventofcode.common.Tuple;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day11 extends Day<List<String>, Integer, Integer> {

    public static final int CELL_SIZE = 50;
    private static final int SIZE = 10;
    private static final int NB_STEPS_PART_1 = 100;


    public Day11() {
        super(11);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Tuple<Integer, Integer> resolvePart1AndPart2(List<String> input) {
        List<Cell> cells = initializeCells(input);
        int step = 0;
        int nbFlashes = 0;
        while (true) {
            step++;
            Set<Cell> cellsFlashes = new HashSet<>();
            cells.forEach(cell -> cell.incrementValueAndFlash(cellsFlashes));
            if (step <= NB_STEPS_PART_1) {
                nbFlashes += cellsFlashes.size();
            }
            if (cellsFlashes.size() == cells.size()) {
                return Tuple.of(nbFlashes, step);
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

    public void simulation() {
        try (
                ImageOutputStream output = new FileImageOutputStream(new File(getSimulationFilename("gif")));
                GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_RGB, 250, false)
        ) {
            List<Cell> cells = initializeCells(getInput());
            writer.writeToSequence(generateBufferedImage(cells));
            int step = 0;
            while (step < NB_STEPS_PART_1) {
                step++;
                Set<Cell> cellsFlashes = new HashSet<>();
                cells.forEach(cell -> cell.incrementValueAndFlash(cellsFlashes));
                writer.writeToSequence(generateBufferedImage(cells));
            }
        } catch (IOException e) {
            System.err.println("Error while generating simulation");
            e.printStackTrace();
        }
    }

    private RenderedImage generateBufferedImage(List<Cell> cells) {
        BufferedImage bufferedImage = new BufferedImage(CELL_SIZE * SIZE, CELL_SIZE * SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        for (Cell cell : cells) {
            if (cell.value == 0) {
                g2d.setColor(new Color(255, 255, 0));
            } else {
                int value = cell.value * 26;
                g2d.setColor(new Color(value, value, 0));
            }
            g2d.fillRect(cell.x * CELL_SIZE, cell.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        g2d.dispose();
        return bufferedImage;
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
