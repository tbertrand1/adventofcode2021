package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
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

    protected abstract O1 resolvePart1(I input);

    protected String formatPart1Result(O1 result1) {
        return result1.toString();
    }

    protected abstract O2 resolvePart2(I input);

    protected String formatPart2Result(O2 result2) {
        return result2.toString();
    }

    public void resolveDay() {
        long inputStart = System.currentTimeMillis();
        I input = getInput();
        long inputElapsedTime = System.currentTimeMillis() - inputStart;

        long part1Start = System.currentTimeMillis();
        O1 result1 = resolvePart1(input);
        long part1ElapsedTime = System.currentTimeMillis() - part1Start;

        long part2Start = System.currentTimeMillis();
        O2 result2 = resolvePart2(input);
        long part2ElapsedTime = System.currentTimeMillis() - part2Start;

        System.out.printf(
                "Day %s (%d ms | %d ms | %d ms): %s | %s\n",
                getDayNumberFormatted(),
                inputElapsedTime,
                part1ElapsedTime,
                part2ElapsedTime,
                formatPart1Result(result1),
                formatPart2Result(result2)
        );
    }

    public void simulation() {
    }
}
