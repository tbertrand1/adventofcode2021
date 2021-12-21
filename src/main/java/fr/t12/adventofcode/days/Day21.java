package fr.t12.adventofcode.days;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day21 extends Day<List<String>, Integer, Long> {

    private static final int[] SUMS_3_ROLLS_DIRAC_DICE_FREQUENCIES = new int[]{0, 0, 0, 1, 3, 6, 7, 6, 3, 1};

    public Day21() {
        super(21);
    }

    private static int extractInitialPosition(String line) {
        return Integer.parseInt(line.split(": ")[1]);
    }

    private static CountWinners simulateGame(Map<GameState, CountWinners> countWinnersByGameState, GameState gameState) {
        if (countWinnersByGameState.containsKey(gameState)) {
            return countWinnersByGameState.get(gameState);
        }

        CountWinners countWinners = new CountWinners(0L, 0L);

        for (int sum = 0; sum < SUMS_3_ROLLS_DIRAC_DICE_FREQUENCIES.length; sum++) {
            int frequency = SUMS_3_ROLLS_DIRAC_DICE_FREQUENCIES[sum];
            if (frequency == 0) {
                continue;
            }
            int newPosition = (gameState.getPlayerPosition() + sum - 1) % 10 + 1;
            GameState nextGameState = gameState.nextGameState(newPosition);
            if (nextGameState.isPlayer1Winner()) {
                countWinners.nbWinsP1 += frequency;
            } else if (nextGameState.isPlayer2Winner()) {
                countWinners.nbWinsP2 += frequency;
            } else {
                CountWinners subCountWinners = simulateGame(countWinnersByGameState, nextGameState);
                countWinners.nbWinsP1 += frequency * subCountWinners.nbWinsP1;
                countWinners.nbWinsP2 += frequency * subCountWinners.nbWinsP2;
            }
        }

        countWinnersByGameState.put(gameState, countWinners);
        return countWinners;
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        int[] positions = {extractInitialPosition(input.get(0)), extractInitialPosition(input.get(1))};
        int[] scores = {0, 0};

        DeterministicDice dice = new DeterministicDice();
        int playerTurn = 0;
        while (true) {
            int moveValue = dice.getSumNext3Rolls();
            int newPosition = (positions[playerTurn] + moveValue - 1) % 10 + 1;
            positions[playerTurn] = newPosition;
            scores[playerTurn] += newPosition;
            if (scores[playerTurn] >= 1000) {
                return scores[1 - playerTurn] * dice.getNbRolls();
            }
            playerTurn = 1 - playerTurn;
        }
    }

    @Override
    protected Long resolvePart2(List<String> input) {
        int positionP1 = extractInitialPosition(input.get(0));
        int positionP2 = extractInitialPosition(input.get(1));
        GameState start = new GameState(positionP1, 0, positionP2, 0, 1);

        CountWinners countWinners = simulateGame(new HashMap<>(), start);
        return Math.max(countWinners.nbWinsP1, countWinners.nbWinsP2);
    }

    static class DeterministicDice {
        private int value = 0;
        private int nbRolls = 0;

        public int getSumNext3Rolls() {
            int sum = 0;
            for (int i = 0; i < 3; i++) {
                this.value = this.value == 100 ? 1 : this.value + 1;
                sum += this.value;
            }
            this.nbRolls += 3;
            return sum;
        }

        public int getNbRolls() {
            return nbRolls;
        }
    }

    static final class CountWinners {
        private long nbWinsP1;
        private long nbWinsP2;

        CountWinners(long nbWinsP1, long nbWinsP2) {
            this.nbWinsP1 = nbWinsP1;
            this.nbWinsP2 = nbWinsP2;
        }
    }

    record GameState(int positionP1, int scoreP1, int positionP2, int scoreP2, int playerTurn) {

        public int getPlayerPosition() {
            return isPlayer1Turn() ? positionP1 : positionP2;
        }

        private boolean isPlayer1Turn() {
            return playerTurn == 1;
        }

        private boolean isPlayer1Winner() {
            return scoreP1 >= 21;
        }

        private boolean isPlayer2Winner() {
            return scoreP2 >= 21;
        }

        public GameState nextGameState(int newPosition) {
            if (isPlayer1Turn()) {
                return new GameState(newPosition, scoreP1 + newPosition, positionP2, scoreP2, 2);
            } else {
                return new GameState(positionP1, scoreP1, newPosition, scoreP2 + newPosition, 1);
            }
        }
    }
}
