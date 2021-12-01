package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class Day<I, O1, O2> {

    private final int dayNumber;

    protected Day(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    protected String getDayNumberFormatted() {
        return StringUtils.leftPad(String.valueOf(dayNumber), 2, '0');
    }

    protected String getInputFilename() {
        return String.format("inputs/day%s.txt", getDayNumberFormatted());
    }

    protected String readInputAsString() {
        return FileUtil.readFile(getInputFilename());
    }

    protected List<String> readInputAsListOfString() {
        return FileUtil.readFileLines(getInputFilename());
    }

    protected List<Integer> readInputAsListOfInteger() {
        return readInputAsListOfString()
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public abstract I getInput();

    public abstract O1 resolvePart1(I input);

    public abstract O2 resolvePart2(I input);

    public void resolveDay() {
        System.out.printf("Day %s\n", getDayNumberFormatted());

        I input = getInput();

        StopWatch watch = new StopWatch();
        watch.start();
        O1 result1 = resolvePart1(input);
        watch.stop();
        System.out.printf("\tPart 1: %s (%d ms)\n", result1, watch.getTime(TimeUnit.MILLISECONDS));
        watch.reset();

        watch.start();
        O2 result2 = resolvePart2(input);
        watch.stop();
        System.out.printf("\tPart 2: %s (%d ms)\n", result2, watch.getTime(TimeUnit.MILLISECONDS));
    }
}
