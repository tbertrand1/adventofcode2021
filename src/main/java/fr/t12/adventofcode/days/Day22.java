package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 extends Day<List<Day22.Step>, Long, Long> {
    private static final int SIZE_PART_1 = 50;

    public Day22() {
        super(22);
    }

    @Override
    protected List<Step> getInput() {
        return readInputAsItems(Step::parse);
    }

    @Override
    protected Tuple<Long, Long> resolvePart1AndPart2(List<Step> input) {
        List<Step> steps = input.stream()
                .filter(step -> step.region.isInsidePart1Region())
                .toList();
        return Tuple.of(countLitCubes(steps), countLitCubes(input));
    }

    private Long countLitCubes(List<Step> input) {
        List<Region> litRegions = new ArrayList<>();
        for (Step step : input) {
            List<Region> nextLitRegions = new ArrayList<>();
            for (Region litRegion : litRegions) {
                if (litRegion.isIntersecting(step.region)) {
                    // If the step's region intersects with the lit region, we split the lit region into smaller regions
                    // to remove the step's region.
                    // If the step's region is lit, this allows to remove the cubes in common to avoid duplication
                    // If the step's region is not lit, this allows to remove unlit cubes
                    nextLitRegions.addAll(litRegion.splitRegionToRemoveOtherRegion(step.region));
                } else {
                    nextLitRegions.add(litRegion);
                }
            }
            if (step.action == Action.ON) {
                nextLitRegions.add(step.region);
            }
            litRegions = nextLitRegions;
        }

        return litRegions.stream()
                .mapToLong(Region::calculateVolume)
                .reduce(Long::sum)
                .orElseThrow();
    }

    enum Action {
        ON, OFF
    }

    record Step(Action action, Region region) {
        private static final Pattern STEP_PATTERN = Pattern.compile("^(.*) x=(.*)\\.\\.(.*),y=(.*)\\.\\.(.*),z=(.*)\\.\\.(.*)$");

        public static Step parse(String line) {
            Matcher matcher = STEP_PATTERN.matcher(line);
            if (matcher.find()) {
                Region region = new Region(
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)),
                        Integer.parseInt(matcher.group(6)),
                        Integer.parseInt(matcher.group(7))
                );
                return new Step(Action.valueOf(matcher.group(1).toUpperCase()), region);
            }
            throw new IllegalStateException("Invalid input");
        }
    }

    record Region(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {

        public boolean isInsidePart1Region() {
            return this.minX >= -SIZE_PART_1 && this.maxX <= SIZE_PART_1
                    && this.minY >= -SIZE_PART_1 && this.maxY <= SIZE_PART_1
                    && this.minZ >= -SIZE_PART_1 && this.maxZ <= SIZE_PART_1;
        }

        public boolean isIntersecting(Region otherRegion) {
            return this.maxX >= otherRegion.minX && this.minX <= otherRegion.maxX
                    && this.maxY >= otherRegion.minY && this.minY <= otherRegion.maxY
                    && this.maxZ >= otherRegion.minZ && this.minZ <= otherRegion.maxZ;
        }

        public List<Region> splitRegionToRemoveOtherRegion(Region otherRegion) {
            List<Region> splitted = new ArrayList<>();
            if (this.minX < otherRegion.minX) {
                splitted.add(new Region(this.minX, otherRegion.minX - 1, this.minY, this.maxY, this.minZ, this.maxZ));
            }
            if (this.maxX > otherRegion.maxX) {
                splitted.add(new Region(otherRegion.maxX + 1, this.maxX, this.minY, this.maxY, this.minZ, this.maxZ));
            }

            int splittedMinX = Math.max(this.minX, otherRegion.minX);
            int splittedMaxX = Math.min(this.maxX, otherRegion.maxX);

            if (this.minY < otherRegion.minY) {
                splitted.add(new Region(splittedMinX, splittedMaxX, this.minY, otherRegion.minY - 1, this.minZ, this.maxZ));
            }
            if (this.maxY > otherRegion.maxY) {
                splitted.add(new Region(splittedMinX, splittedMaxX, otherRegion.maxY + 1, this.maxY, this.minZ, this.maxZ));
            }

            int splittedMinY = Math.max(this.minY, otherRegion.minY);
            int splittedMaxY = Math.min(this.maxY, otherRegion.maxY);

            if (this.minZ < otherRegion.minZ) {
                splitted.add(new Region(splittedMinX, splittedMaxX, splittedMinY, splittedMaxY, this.minZ, otherRegion.minZ - 1));
            }
            if (this.maxZ > otherRegion.maxZ) {
                splitted.add(new Region(splittedMinX, splittedMaxX, splittedMinY, splittedMaxY, otherRegion.maxZ + 1, this.maxZ));
            }

            return splitted;
        }

        public long calculateVolume() {
            return (long) (this.maxX - this.minX + 1) * (this.maxY - this.minY + 1) * (this.maxZ - this.minZ + 1);
        }
    }
}
