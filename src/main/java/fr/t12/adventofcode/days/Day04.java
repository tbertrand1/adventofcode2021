package fr.t12.adventofcode.days;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;

public class Day04 extends Day<List<String>, Integer, Integer> {

    public Day04() {
        super(4);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        List<Integer> drawnNumbers = parseDrawnNumbers(input);
        List<BingoBoard> boards = createBingoBoards(input);

        for (Integer drawnNumber : drawnNumbers) {
            for (BingoBoard bingoBoard : boards) {
                if (bingoBoard.markNumberAndCheckIsWinner(drawnNumber)) {
                    return bingoBoard.getFinalScore(drawnNumber);
                }
            }
        }

        throw new IllegalStateException("No solution found");
    }

    private List<Integer> parseDrawnNumbers(List<String> input) {
        return Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).toList();
    }

    private List<BingoBoard> createBingoBoards(List<String> input) {
        List<BingoBoard> boards = new ArrayList<>();
        boards.add(new BingoBoard());

        int index = 2;
        while (index < input.size()) {
            String line = input.get(index);
            if (StringUtils.isBlank(line)) {
                boards.add(new BingoBoard());
            } else {
                boards.get(boards.size() - 1).addCells(line);
            }
            index++;
        }
        return boards;
    }

    @Override
    protected Integer resolvePart2(List<String> input) {
        List<Integer> drawnNumbers = parseDrawnNumbers(input);
        List<BingoBoard> boards = createBingoBoards(input);

        List<BingoBoard> winnerBoards = new ArrayList<>();
        for (Integer drawnNumber : drawnNumbers) {
            List<BingoBoard> remainingBoards = boards.stream().filter(board -> !winnerBoards.contains(board)).toList();
            for (BingoBoard bingoBoard : remainingBoards) {
                if (bingoBoard.markNumberAndCheckIsWinner(drawnNumber)) {
                    if (boards.size() - winnerBoards.size() == 1) {
                        return bingoBoard.getFinalScore(drawnNumber);
                    } else {
                        winnerBoards.add(bingoBoard);
                    }
                }
            }
        }

        throw new IllegalStateException("No solution found");
    }

    static class BingoBoard {
        private final Map<Integer, BingoCell> cellsByNumber = new HashMap<>();
        private int nbLines = 0;

        public void addCells(String line) {
            List<Integer> numbers = Arrays.stream(line.trim().split("\s+")).map(Integer::parseInt).toList();
            for (int i = 0; i < numbers.size(); i++) {
                cellsByNumber.put(numbers.get(i), new BingoCell(numbers.get(i), nbLines, i));
            }
            nbLines++;
        }

        public boolean markNumberAndCheckIsWinner(int number) {
            if (cellsByNumber.containsKey(number)) {
                BingoCell cell = cellsByNumber.get(number);
                cell.mark();
                return isBoardWinner(cell);
            }
            return false;
        }

        private boolean isBoardWinner(BingoCell lastCell) {
            return getCellsOfRow(lastCell).stream().allMatch(BingoCell::isMarked) ||
                    getCellsOfColumn(lastCell).stream().allMatch(BingoCell::isMarked);
        }

        public int getFinalScore(Integer lastNumber) {
            int unmarked = cellsByNumber.values().stream().filter(cell -> !cell.isMarked()).mapToInt(BingoCell::getNumber).sum();
            return unmarked * lastNumber;
        }

        private List<BingoCell> getCellsOfRow(BingoCell currentCell) {
            return cellsByNumber.values().stream().filter(cell -> cell.getRow() == currentCell.getRow()).toList();
        }

        private List<BingoCell> getCellsOfColumn(BingoCell currentCell) {
            return cellsByNumber.values().stream().filter(cell -> cell.getColumn() == currentCell.getColumn()).toList();
        }
    }

    static class BingoCell {
        private final int number;
        private final int row;
        private final int column;
        private boolean marked;

        public BingoCell(int number, int row, int column) {
            this.number = number;
            this.row = row;
            this.column = column;
        }

        public int getNumber() {
            return number;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean isMarked() {
            return marked;
        }

        public void mark() {
            marked = true;
        }
    }
}
