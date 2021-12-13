package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Day13 extends Day<List<String>, Integer, Day13.Paper> {

    public Day13() {
        super(13);
    }

    @Override
    protected String formatPart2Result(Paper paper) {
        StringBuilder part2 = new StringBuilder("\n");
        paper.toLines().forEach(line -> part2.append("\t").append(line).append("\n"));
        return part2.toString();
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        Paper paper = resolve(input, true);
        return paper.countDots();
    }

    @Override
    protected Paper resolvePart2(List<String> input) {
        return resolve(input, false);
    }

    private Paper resolve(List<String> input, boolean onlyFirstInstruction) {
        Tuple<List<Dot>, List<Instruction>> tuple = parseInput(input);
        Paper paper = new Paper(tuple.first());
        for (Instruction instruction : tuple.second()) {
            paper.applyInstruction(instruction);
            if (onlyFirstInstruction) {
                break;
            }
        }
        return paper;
    }

    private Tuple<List<Dot>, List<Instruction>> parseInput(List<String> input) {
        List<Dot> dots = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        for (String line : input) {
            if (StringUtils.isNotBlank(line)) {
                if (line.startsWith("fold along")) {
                    instructions.add(Instruction.parse(line));
                } else {
                    dots.add(Dot.parse(line));
                }
            }
        }
        return new Tuple<>(dots, instructions);
    }

    record Dot(int x, int y) {
        private static Dot parse(String line) {
            String[] split = line.split(",");
            return new Dot(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }

    record Instruction(boolean horizontal, int value) {
        private static Instruction parse(String line) {
            return new Instruction(
                    line.contains("y"),
                    Integer.parseInt(line.substring(line.indexOf("=") + 1))
            );
        }
    }

    static class Paper {
        private int width;
        private int height;
        private boolean[][] cells;

        protected Paper(List<Dot> dots) {
            width = dots.stream().mapToInt(Dot::x).max().orElseThrow() + 1;
            height = dots.stream().mapToInt(Dot::y).max().orElseThrow() + 1;

            cells = new boolean[height][];
            for (int y = 0; y < height; y++) {
                cells[y] = new boolean[width];
            }
            for (Dot dot : dots) {
                cells[dot.y][dot.x] = true;
            }
        }

        protected void applyInstruction(Instruction instruction) {
            int newWidth = instruction.horizontal ? width : instruction.value;
            int newHeight = instruction.horizontal ? instruction.value : height;

            boolean[][] newCells = new boolean[newHeight][];
            for (int y = 0; y < newHeight; y++) {
                newCells[y] = new boolean[newWidth];
                for (int x = 0; x < newWidth; x++) {
                    newCells[y][x] = cells[y][x];
                }
                for (int x = 1; x < width - newWidth; x++) {
                    newCells[y][newWidth - x] |= cells[y][newWidth + x];
                }
            }
            for (int y = 1; y < height - newHeight; y++) {
                for (int x = 0; x < width; x++) {
                    newCells[newHeight - y][x] |= cells[newHeight + y][x];
                }
            }
            width = newWidth;
            height = newHeight;
            cells = newCells;
        }

        protected Integer countDots() {
            int nbDots = 0;
            for (boolean[] row : cells) {
                for (boolean dot : row) {
                    if (dot) {
                        nbDots++;
                    }
                }
            }
            return nbDots;
        }

        protected List<String> toLines() {
            List<String> lines = new ArrayList<>();
            for (boolean[] row : cells) {
                StringBuilder line = new StringBuilder();
                for (boolean dot : row) {
                    line.append(dot ? "█" : " ");
                }
                lines.add(line.toString());
            }
            return lines;
        }
    }
}
