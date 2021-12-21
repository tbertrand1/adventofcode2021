package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day17 extends Day<String, Integer, Integer> {

    public Day17() {
        super(17);
    }

    @Override
    protected String getInput() {
        return readInputAsString();
    }

    @Override
    protected Tuple<Integer, Integer> resolvePart1AndPart2(String input) {
        Target target = Target.parse(input);
        List<Integer> highestVelocities = target.getVelocitiesToCheck()
                .stream()
                .map(target::runSteps)
                .flatMap(Optional::stream)
                .toList();
        return Tuple.of(
                highestVelocities.stream().reduce(Integer::max).orElseThrow(),
                highestVelocities.size()
        );
    }

    record Point(int x, int y) {
        public Point nextPosition(Velocity velocity) {
            return new Point(this.x + velocity.x, this.y + velocity.y);
        }
    }

    record Velocity(int x, int y) {
        public Velocity nextVelocity() {
            return new Velocity(
                    this.x > 0 ? this.x - 1 : (this.x < 0 ? this.x + 1 : this.x),
                    this.y - 1
            );
        }
    }

    record Target(Point topLeft, Point bottomRight) {
        private static final Pattern TARGET_PATTERN = Pattern.compile("target area: x=(.*)\\.\\.(.*), y=(.*)\\.\\.(.*)");

        public static Target parse(String input) {
            Matcher matcher = TARGET_PATTERN.matcher(input);
            if (matcher.find()) {
                return new Target(
                        new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(4))),
                        new Point(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)))
                );
            }
            throw new IllegalStateException("Invalid input");
        }

        public Optional<Integer> runSteps(Velocity initialVelocity) {
            Point position = new Point(0, 0);
            Velocity velocity = initialVelocity;
            int highest = 0;
            while (position.y > this.bottomRight.y && position.x <= this.bottomRight.x) {
                position = position.nextPosition(velocity);
                highest = Math.max(highest, position.y);
                if (this.inArea(position)) {
                    return Optional.of(highest);
                }
                velocity = velocity.nextVelocity();
            }
            return Optional.empty();
        }

        private boolean inArea(Point point) {
            return point.x >= this.topLeft.x && point.x <= this.bottomRight.x
                    && point.y >= this.bottomRight.y && point.y <= this.topLeft.y;
        }

        public List<Velocity> getVelocitiesToCheck() {
            return IntStream.range(1, this.bottomRight.x + 1)
                    .mapToObj(x -> {
                        int negativeY = -Math.abs(this.bottomRight.y);
                        return IntStream.range(negativeY, -negativeY)
                                .mapToObj(y -> new Velocity(x, y))
                                .toList();
                    })
                    .flatMap(Collection::stream)
                    .toList();
        }
    }
}
