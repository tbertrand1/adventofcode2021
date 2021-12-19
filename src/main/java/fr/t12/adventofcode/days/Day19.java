package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 extends Day<String, Integer, Integer> {
    private static final int NB_ORIENTATION = 48;
    private static final int COMMON_BEACONS_EXPECTED = 12;

    public Day19() {
        super(19);
    }

    @Override
    protected String getInput() {
        return readInputAsString();
    }

    @Override
    protected Integer resolvePart1(String input) {
        ScannerWithOrientation[][] allScannersWithOrientations = Scanner.parseScanners(input);
        Tuple<ScannerWithOrientation[], Position[]> scannerOrientationAndPosition = resolve(allScannersWithOrientations);

        Set<Position> allBeaconsRelatives = new HashSet<>();
        for (int i = 0; i < allScannersWithOrientations.length; i++) {
            ScannerWithOrientation scanner = scannerOrientationAndPosition.first()[i];
            allBeaconsRelatives.addAll(scanner.getAllBeaconsFromRelativePosition(scannerOrientationAndPosition.second()[i]));
        }
        return allBeaconsRelatives.size();
    }

    @Override
    protected Integer resolvePart2(String input) {
        ScannerWithOrientation[][] allScannersWithOrientations = Scanner.parseScanners(input);
        Tuple<ScannerWithOrientation[], Position[]> scannerOrientationAndPosition = resolve(allScannersWithOrientations);

        int maxDistance = 0;
        for (Position scannerA : scannerOrientationAndPosition.second()) {
            for (Position scannerB : scannerOrientationAndPosition.second()) {
                int distance = scannerA.calculateDistance(scannerB);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    private Tuple<ScannerWithOrientation[], Position[]> resolve(ScannerWithOrientation[][] allScannersWithOrientations) {
        int nbScanners = allScannersWithOrientations.length;

        Position[] scannersPositions = new Position[nbScanners];
        scannersPositions[0] = new Position(0, 0, 0);

        ScannerWithOrientation[] scannersWithOrientationValidated = new ScannerWithOrientation[nbScanners];
        scannersWithOrientationValidated[0] = allScannersWithOrientations[0][0];

        Queue<Integer> scannersIndexesToCheck = new ArrayDeque<>();
        scannersIndexesToCheck.add(0);

        while (!scannersIndexesToCheck.isEmpty()) {
            Integer currIndex = scannersIndexesToCheck.poll();
            ScannerWithOrientation currScanner = scannersWithOrientationValidated[currIndex];
            Position currPosition = scannersPositions[currIndex];
            for (int i = 1; i < nbScanners; i++) {
                if (scannersPositions[i] == null) {
                    ScannerWithOrientation[] allScannerOrientations = allScannersWithOrientations[i];
                    if (currScanner.hasEnoughBeaconsInCommon(allScannerOrientations[0])) {
                        Tuple<ScannerWithOrientation, Position> scannerOrientationAndPosition =
                                currScanner.determineOtherScannerOrientationAndPosition(allScannerOrientations);
                        if (scannerOrientationAndPosition.isNotEmpty()) {
                            scannersWithOrientationValidated[i] = scannerOrientationAndPosition.first();
                            Position relativePosition = scannerOrientationAndPosition.second();
                            scannersPositions[i] = new Position(
                                    currPosition.x - relativePosition.x,
                                    currPosition.y - relativePosition.y,
                                    currPosition.z - relativePosition.z
                            );
                            scannersIndexesToCheck.add(i);
                        }
                    }
                }
            }
        }

        return Tuple.of(scannersWithOrientationValidated, scannersPositions);
    }

    record Position(int x, int y, int z) {

        public List<Position> generateAllOrientations() {
            return List.of(
                    new Position(x, y, z),
                    new Position(-x, y, z),
                    new Position(x, -y, z),
                    new Position(x, y, -z),
                    new Position(-x, -y, z),
                    new Position(x, -y, -z),
                    new Position(-x, y, -z),
                    new Position(-x, -y, -z),
                    new Position(x, z, y),
                    new Position(-x, z, y),
                    new Position(x, -z, y),
                    new Position(x, z, -y),
                    new Position(-x, -z, y),
                    new Position(x, -z, -y),
                    new Position(-x, z, -y),
                    new Position(-x, -z, -y),
                    new Position(y, x, z),
                    new Position(-y, x, z),
                    new Position(y, -x, z),
                    new Position(y, x, -z),
                    new Position(-y, -x, z),
                    new Position(y, -x, -z),
                    new Position(-y, x, -z),
                    new Position(-y, -x, -z),
                    new Position(y, z, x),
                    new Position(-y, z, x),
                    new Position(y, -z, x),
                    new Position(y, z, -x),
                    new Position(-y, -z, x),
                    new Position(y, -z, -x),
                    new Position(-y, z, -x),
                    new Position(-y, -z, -x),
                    new Position(z, x, y),
                    new Position(-z, x, y),
                    new Position(z, -x, y),
                    new Position(z, x, -y),
                    new Position(-z, -x, y),
                    new Position(z, -x, -y),
                    new Position(-z, x, -y),
                    new Position(-z, -x, -y),
                    new Position(z, y, x),
                    new Position(-z, y, x),
                    new Position(z, -y, x),
                    new Position(z, y, -x),
                    new Position(-z, -y, x),
                    new Position(z, -y, -x),
                    new Position(-z, y, -x),
                    new Position(-z, -y, -x)
            );
        }

        public int calculateDistance(Position other) {
            return Math.abs(other.x - this.x) + Math.abs(other.y - this.y) + Math.abs(other.z - this.z);
        }
    }

    static final class Scanner {
        private static final Pattern SCANNER_HEADER_PATTERN = Pattern.compile("--- scanner (\\d+) ---");
        private final int id;
        private final List<Position> beacons = new ArrayList<>();

        Scanner(int id) {
            this.id = id;
        }

        public static ScannerWithOrientation[][] parseScanners(String input) {
            List<Scanner> scanners = new ArrayList<>();
            for (String scannerInput : input.split("\r?\n\r?\n")) {
                scanners.add(parseScanner(scannerInput));
            }

            // For each scanner, generate the list of all orientations
            ScannerWithOrientation[][] allScannersOrientations = new ScannerWithOrientation[scanners.size()][NB_ORIENTATION];
            for (int i = 0; i < scanners.size(); i++) {
                allScannersOrientations[i] = scanners.get(i).generateAllOrientations();
            }
            return allScannersOrientations;
        }

        private static Scanner parseScanner(String scannerInput) {
            String[] lines = scannerInput.split("\r?\n");

            Matcher matcher = SCANNER_HEADER_PATTERN.matcher(lines[0]);
            if (matcher.find()) {
                Scanner scanner = new Scanner(Integer.parseInt(matcher.group(1)));
                for (int i = 1; i < lines.length; i++) {
                    String[] split = lines[i].split(",");
                    scanner.beacons.add(new Position(
                            Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]),
                            Integer.parseInt(split[2])
                    ));
                }
                return scanner;
            } else {
                throw new IllegalStateException("Invalid header");
            }
        }

        private ScannerWithOrientation[] generateAllOrientations() {
            int[][] distancesBetweenBeacons = calculateDistancesBetweenBeacons();

            ScannerWithOrientation[] scanners = new ScannerWithOrientation[NB_ORIENTATION];
            for (int i = 0; i < NB_ORIENTATION; i++) {
                scanners[i] = new ScannerWithOrientation(this.id, distancesBetweenBeacons, i);
            }
            for (Position beacon : this.beacons) {
                List<Position> beaconOrientations = beacon.generateAllOrientations();
                for (int i = 0; i < NB_ORIENTATION; i++) {
                    scanners[i].beacons.add(beaconOrientations.get(i));
                }
            }
            return scanners;
        }

        private int[][] calculateDistancesBetweenBeacons() {
            int[][] distancesBetweenBeacons = new int[this.beacons.size()][this.beacons.size()];
            for (int i = 0; i < this.beacons.size(); i++) {
                Position beaconI = this.beacons.get(i);
                for (int j = 0; j < this.beacons.size(); j++) {
                    Position beaconJ = this.beacons.get(j);
                    distancesBetweenBeacons[i][j] = beaconI.equals(beaconJ) ? 0 : beaconI.calculateDistance(beaconJ);
                }
            }
            return distancesBetweenBeacons;
        }
    }

    static final class ScannerWithOrientation {
        private final int id;
        private final List<Position> beacons = new ArrayList<>();
        private final int[][] distancesBetweenBeacons;
        private final int orientation;

        public ScannerWithOrientation(int id, int[][] distancesBetweenBeacons, int orientation) {
            this.id = id;
            this.distancesBetweenBeacons = distancesBetweenBeacons;
            this.orientation = orientation;
        }

        public boolean hasEnoughBeaconsInCommon(ScannerWithOrientation other) {
            // Determination of beacons in common is based on distance between beacons.
            // Even if the axes are not good, distance is still the same
            for (int i = 0; i < this.beacons.size(); i++) {
                int[] distances = this.distancesBetweenBeacons[i];
                for (int j = 0; j < other.beacons.size(); j++) {
                    int[] otherDistances = other.distancesBetweenBeacons[j];
                    int countInCommon = 0;
                    for (int distance : distances) {
                        for (int otherDistance : otherDistances) {
                            if (distance == otherDistance) {
                                countInCommon++;
                                if (countInCommon == COMMON_BEACONS_EXPECTED) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

        public Tuple<ScannerWithOrientation, Position> determineOtherScannerOrientationAndPosition(ScannerWithOrientation[] otherAllOrientations) {
            for (ScannerWithOrientation otherOrientation : otherAllOrientations) {
                for (Position beacon : this.beacons) {
                    for (Position otherBeacon : otherOrientation.beacons) {
                        Position otherScannerPosition = new Position(
                                beacon.x - otherBeacon.x,
                                beacon.y - otherBeacon.y,
                                beacon.z - otherBeacon.z
                        );
                        Tuple<ScannerWithOrientation, Position> orientationAndPosition = checkOtherScannerPositionValid(otherOrientation, otherScannerPosition);
                        if (orientationAndPosition.isNotEmpty()) {
                            return orientationAndPosition;
                        }
                    }
                }
            }
            return Tuple.empty();
        }

        private Tuple<ScannerWithOrientation, Position> checkOtherScannerPositionValid(ScannerWithOrientation otherScanner, Position otherScannerPosition) {
            int countInCommon = 0;
            for (Position beacon : this.beacons) {
                for (Position otherBeacon : otherScanner.beacons) {
                    if ((otherScannerPosition.x + otherBeacon.x == beacon.x)
                            && (otherScannerPosition.y + otherBeacon.y == beacon.y)
                            && (otherScannerPosition.z + otherBeacon.z == beacon.z)) {
                        countInCommon++;
                        if (countInCommon == COMMON_BEACONS_EXPECTED) {
                            return Tuple.of(otherScanner, otherScannerPosition);
                        }
                    }
                }
            }
            return Tuple.empty();
        }

        public List<Position> getAllBeaconsFromRelativePosition(Position scannerPosition) {
            return this.beacons.stream()
                    .map(p -> new Position(p.x - scannerPosition.x, p.y - scannerPosition.y, p.z - scannerPosition.z))
                    .toList();
        }
    }
}
