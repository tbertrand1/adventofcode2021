package fr.t12.adventofcode.days;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.*;
import java.util.function.Function;

public class Day12 extends Day<List<String>, Integer, Integer> {

    public Day12() {
        super(12);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        return parseCavesAndCountPaths(input, false);
    }

    @Override
    protected Integer resolvePart2(List<String> input) {
        return parseCavesAndCountPaths(input, true);
    }

    private Integer parseCavesAndCountPaths(List<String> input, boolean canVisitOneSmallCaveTwice) {
        List<Cave> caves = parseCaves(input);
        LinkedList<Cave> path = new LinkedList<>();
        path.add(caves.stream().filter(c -> c.start).findFirst().orElseThrow());
        MutableInt nbPathsFound = new MutableInt();
        findPaths(path, nbPathsFound, !canVisitOneSmallCaveTwice);
        return nbPathsFound.getValue();
    }

    private List<Cave> parseCaves(List<String> input) {
        Map<String, Cave> cavesByName = new HashMap<>();
        for (String line : input) {
            String[] split = line.split("-");
            Cave cave1 = cavesByName.computeIfAbsent(split[0], Cave::new);
            Cave cave2 = cavesByName.computeIfAbsent(split[1], Cave::new);
            cave1.connectTo(cave2);
        }
        return cavesByName.values().stream().toList();
    }

    private void findPaths(LinkedList<Cave> path, MutableInt nbPathsFound, boolean hasVisitOneSmallCaveTwice) {
        for (Cave connectedCave : path.getLast().connectedCaves) {
            if (connectedCave.start) {
                continue;
            }
            if (connectedCave.end) {
                nbPathsFound.increment();
                continue;
            }
            boolean localHasVisitOneSmallCaveTwice = hasVisitOneSmallCaveTwice;
            if (connectedCave.small && path.contains(connectedCave)) {
                if (localHasVisitOneSmallCaveTwice) {
                    continue;
                } else {
                    localHasVisitOneSmallCaveTwice = true;
                }
            }
            path.addLast(connectedCave);
            findPaths(path, nbPathsFound, localHasVisitOneSmallCaveTwice);
            path.removeLast();
        }
    }

    static class Cave {
        private final String name;
        private final boolean start;
        private final boolean end;
        private final boolean small;
        private final List<Cave> connectedCaves = new ArrayList<>();

        public Cave(String name) {
            this.name = name;
            this.start = name.equals("start");
            this.end = name.equals("end");
            this.small = !this.start && !this.end && this.name.equals(this.name.toLowerCase());
        }

        public void connectTo(Cave cave) {
            this.connectedCaves.add(cave);
            cave.connectedCaves.add(this);
        }

        @Override
        public String toString() {
            return String.format("Cave %s", this.name);
        }
    }
}
