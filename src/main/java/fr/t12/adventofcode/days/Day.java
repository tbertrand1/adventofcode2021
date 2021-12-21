package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import fr.t12.adventofcode.common.Tuple;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Day<I, O1, O2> {

    private final int dayNumber;

    protected Day(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    private String getDayNumberFormatted() {
        return StringUtils.leftPad(String.valueOf(dayNumber), 2, '0');
    }

    private String getInputFilename() {
        return String.format("inputs/day%s.txt", getDayNumberFormatted());
    }

    protected String getSimulationFilename(String extension) {
        File file = new File("simulations");
        if (!file.exists()) {
            file.mkdir();
        }
        return String.format("%s/day%s.%s", file.getPath(), getDayNumberFormatted(), extension);
    }

    protected String readInputAsString() {
        return FileUtil.readFile(getInputFilename());
    }

    protected <T> List<T> readInputAsItems(Function<String, T> parseItemFn) {
        return FileUtil.readFileLines(getInputFilename())
                .stream()
                .map(parseItemFn)
                .collect(Collectors.toList());
    }

    protected abstract I getInput();

    protected Tuple<O1, O2> resolvePart1AndPart2(I input) {
        return Tuple.of(resolvePart1(input), resolvePart2(input));
    }

    protected O1 resolvePart1(I input) {
        return null;
    }

    protected String formatPart1Result(O1 result1) {
        return result1.toString();
    }

    protected O2 resolvePart2(I input) {
        return null;
    }

    protected String formatPart2Result(O2 result2) {
        return result2.toString();
    }

    public void resolveDay() {
        long inputStart = System.currentTimeMillis();
        I input = getInput();
        long inputElapsedTime = System.currentTimeMillis() - inputStart;

        long resolveStart = System.currentTimeMillis();
        Tuple<O1, O2> results = resolvePart1AndPart2(input);
        long resolveElapsedTime = System.currentTimeMillis() - resolveStart;

        System.out.printf(
                "Day %s (%d ms | %d ms): %s | %s\n",
                getDayNumberFormatted(),
                inputElapsedTime,
                resolveElapsedTime,
                formatPart1Result(results.first()),
                formatPart2Result(results.second())
        );
    }

    public void simulation() {
    }
}
