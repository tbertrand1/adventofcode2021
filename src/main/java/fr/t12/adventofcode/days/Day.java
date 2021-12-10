package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import org.apache.commons.lang3.StringUtils;

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

    protected abstract O2 resolvePart2(I input);

    public void resolveDay() {
        System.out.printf("Day %s\n", getDayNumberFormatted());

        I input = getInput();

        long start = System.currentTimeMillis();
        O1 result1 = resolvePart1(input);
        System.out.printf("\tPart 1: %s (%d ms)\n", result1, System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        O2 result2 = resolvePart2(input);
        System.out.printf("\tPart 2: %s (%d ms)\n", result2, System.currentTimeMillis() - start);
    }
}
