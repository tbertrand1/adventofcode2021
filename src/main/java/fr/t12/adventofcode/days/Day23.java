package fr.t12.adventofcode.days;

import java.util.*;
import java.util.function.Function;

public class Day23 extends Day<List<String>, Integer, Integer> {
    private static final int NB_ROOMS = 4;
    private static final int NB_SPACES_HALLWAY = 11;
    private static final Map<Integer, Integer> HALLWAY_INDEX_BY_ROOM_INDEX = Map.of(0, 2, 1, 4, 2, 6, 3, 8);

    public Day23() {
        super(23);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Integer resolvePart1(List<String> input) {
        return resolve(input);
    }

    @Override
    protected Integer resolvePart2(List<String> input) {
        List<String> completedInput = new ArrayList<>();
        completedInput.addAll(input.subList(0, 3));
        completedInput.addAll(List.of("  #D#C#B#A#", "  #D#B#A#C#"));
        completedInput.addAll(input.subList(3, input.size()));
        return resolve(completedInput);
    }

    private int resolve(List<String> lines) {
        State initialState = State.parse(lines);

        PriorityQueue<State> statesToCheck = new PriorityQueue<>(Comparator.comparingInt(State::getCost));
        statesToCheck.add(initialState);

        Map<State, State> cameFromState = new HashMap<>();

        Map<State, Integer> bestCostToState = new HashMap<>();
        bestCostToState.put(initialState, 0);

        while (!statesToCheck.isEmpty()) {
            State state = statesToCheck.poll();
            state.setCost(bestCostToState.getOrDefault(state, Integer.MAX_VALUE));
            if (state.isFinalState()) {
                //displayStates(cameFromState, state);
                return bestCostToState.get(state);
            }

            List<State> nextStates = state.determineNextStates();
            for (State nextState : nextStates) {
                if (nextState.getCost() < bestCostToState.getOrDefault(nextState, Integer.MAX_VALUE)) {
                    bestCostToState.put(nextState, nextState.getCost());
                    //cameFromState.put(nextState, state);
                    statesToCheck.add(nextState);
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }

    private void displayStates(Map<State, State> cameFromState, State finalState) {
        Deque<State> states = new ArrayDeque<>();
        State cur = finalState;
        while (cur != null) {
            states.addFirst(cur);
            cur = cameFromState.get(cur);
        }
        while (!states.isEmpty()) {
            State state = states.pop();
            state.displayState();
        }
    }

    enum Amphipod {
        A(1, 0),
        B(10, 1),
        C(100, 2),
        D(1000, 3);

        private final int costPerMove;
        private final int targetRoomIndex;

        Amphipod(int costPerMove, int targetRoomIndex) {
            this.costPerMove = costPerMove;
            this.targetRoomIndex = targetRoomIndex;
        }

        public static Optional<Amphipod> getAmphipodByValue(String value) {
            return Arrays.stream(Amphipod.values())
                    .filter(pod -> pod.name().equals(value))
                    .findFirst();
        }
    }

    /*
     * #############
     * #01234567890# <- hallway
     * ###0#1#2#3### <- rooms[n][0]
     *   #0#1#2#3#   <- rooms[n][1]
     *   #########
     */
    static final class State {

        private final Hallway hallway;
        private final Room[] rooms;
        private final int roomsDepth;
        private int cost;

        State(Hallway hallway, Room[] rooms, int roomsDepth, int cost) {
            this.hallway = hallway;
            this.rooms = rooms;
            this.roomsDepth = roomsDepth;
            this.cost = cost;
        }

        public static State parse(List<String> lines) {
            int roomsDepth = lines.size() - 3;
            Hallway hallway = Hallway.newEmptyHallway();
            Room[] rooms = new Room[NB_ROOMS];
            for (int hallwayIndex = 0; hallwayIndex < NB_SPACES_HALLWAY; hallwayIndex++) {
                String amphipodValue = Character.toString(lines.get(1).charAt(hallwayIndex + 1));
                Optional<Amphipod> pod = Amphipod.getAmphipodByValue(amphipodValue);
                if (pod.isPresent()) {
                    hallway.addPodAt(hallwayIndex, pod.get());
                }
            }
            for (int roomIndex = 0; roomIndex < NB_ROOMS; roomIndex++) {
                rooms[roomIndex] = Room.newEmptyRoom(roomIndex, roomsDepth);
            }
            for (int roomDepth = 0; roomDepth < roomsDepth; roomDepth++) {
                for (int roomIndex = 0; roomIndex < NB_ROOMS; roomIndex++) {
                    String amphipodValue = Character.toString(lines.get(roomDepth + 2).charAt(3 + roomIndex * 2));
                    Optional<Amphipod> pod = Amphipod.getAmphipodByValue(amphipodValue);
                    if (pod.isPresent()) {
                        rooms[roomIndex].addPodAt(roomDepth, pod.get());
                    }
                }
            }
            return new State(hallway, rooms, roomsDepth, 0);
        }

        public List<State> determineNextStates() {
            List<State> nextStates = determineNextStatesWithMovesFromHallwayToRooms();
            if (!nextStates.isEmpty()) {
                return nextStates;
            }
            return determineNextStatesWithMovesFromRoomsToHallway();
        }

        private List<State> determineNextStatesWithMovesFromHallwayToRooms() {
            List<State> nextStates = new ArrayList<>();

            for (int hallwayIndex = 0; hallwayIndex < NB_SPACES_HALLWAY; hallwayIndex++) {
                Amphipod pod = this.hallway.getPod(hallwayIndex);
                if (pod == null) {
                    continue;
                }

                Room targetedRoom = this.rooms[pod.targetRoomIndex];
                int roomHallwayIndex = targetedRoom.getHallwayIndex();
                boolean moveValid = targetedRoom.isValid() && this.hallway.isMovePossible(hallwayIndex, roomHallwayIndex);
                if (moveValid) {
                    int roomDepth = targetedRoom.getLastIndexEmpty();
                    int nextCost = this.cost + (roomDepth + 1 + Math.abs(hallwayIndex - roomHallwayIndex)) * pod.costPerMove;

                    State nextState = clone();
                    nextState.hallway.removePodAt(hallwayIndex);
                    nextState.rooms[pod.targetRoomIndex].addPodAt(roomDepth, pod);
                    nextState.setCost(nextCost);
                    nextStates.add(nextState);
                }
            }

            return nextStates;
        }

        private List<State> determineNextStatesWithMovesFromRoomsToHallway() {
            List<State> nextStates = new ArrayList<>();

            for (Room room : this.rooms) {
                int roomHallwayIndex = room.getHallwayIndex();
                if (room.isEmpty()) {
                    continue;
                }
                Integer roomDepth = room.getDepthOfPodToMove();
                if (roomDepth == null) {
                    continue;
                }
                Amphipod pod = room.getPod(roomDepth);

                for (int hallwayIndex = 0; hallwayIndex < NB_SPACES_HALLWAY; hallwayIndex++) {
                    if (this.hallway.isRoomEntrance(hallwayIndex)) {
                        continue;
                    }
                    boolean moveValid = this.hallway.isMovePossible(roomHallwayIndex, hallwayIndex);
                    if (moveValid) {
                        int nextCost = this.cost + (roomDepth + 1 + Math.abs(hallwayIndex - roomHallwayIndex)) * pod.costPerMove;

                        State nextState = clone();
                        nextState.hallway.addPodAt(hallwayIndex, pod);
                        nextState.rooms[room.roomIndex].removePodAt(roomDepth);
                        nextState.setCost(nextCost);
                        nextStates.add(nextState);
                    }
                }
            }

            return nextStates;
        }

        private boolean isFinalState() {
            return this.hallway.isEmpty() && Arrays.stream(this.rooms).allMatch(Room::isValid);
        }

        public void displayState() {
            System.out.println("#############");
            for (int depthIndex = 0; depthIndex < this.roomsDepth + 1; depthIndex++) {
                System.out.print(depthIndex <= 1 ? '#' : ' ');
                for (int index = 0; index < NB_SPACES_HALLWAY; index++) {
                    if (depthIndex == 0) {
                        Amphipod pod = this.hallway.getPod(index);
                        System.out.print(pod == null ? '.' : pod);
                    } else if (this.hallway.isRoomEntrance(index)) {
                        int finalIndex = index;
                        int roomIndex = HALLWAY_INDEX_BY_ROOM_INDEX.entrySet().stream()
                                .filter(entry -> entry.getValue() == finalIndex)
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElseThrow();
                        Amphipod pod = this.rooms[roomIndex].getPod(depthIndex - 1);
                        System.out.print(pod == null ? '.' : pod);
                    } else if (this.hallway.isRoomEntrance(index - 1) || this.hallway.isRoomEntrance(index + 1)) {
                        System.out.print('#');
                    } else {
                        System.out.print(depthIndex == 1 ? '#' : ' ');
                    }
                }
                System.out.print(depthIndex <= 1 ? '#' : ' ');
                System.out.println();
            }
            System.out.println("  #########  ");
            System.out.println();
        }

        public int getCost() {
            return this.cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        @Override
        protected State clone() {
            Hallway nextHallway = this.hallway.clone();
            Room[] nextRooms = Arrays.stream(this.rooms).map(Room::clone).toArray(Room[]::new);
            return new State(nextHallway, nextRooms, this.roomsDepth, this.cost);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (State) obj;
            return Objects.equals(this.hallway, that.hallway) && Objects.deepEquals(this.rooms, that.rooms);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hallway, Arrays.hashCode(rooms));
        }
    }

    record Hallway(Amphipod[] pods) {
        public static Hallway newEmptyHallway() {
            return new Hallway(new Amphipod[NB_SPACES_HALLWAY]);
        }

        @Override
        public Hallway clone() {
            return new Hallway(this.pods.clone());
        }

        public Amphipod getPod(int index) {
            return this.pods[index];
        }

        public void addPodAt(int index, Amphipod pod) {
            if (this.pods[index] != null) {
                throw new IllegalStateException("Trying to override pod");
            }
            this.pods[index] = pod;
        }

        public void removePodAt(int index) {
            this.pods[index] = null;
        }

        public boolean isEmpty() {
            return Arrays.stream(this.pods).allMatch(Objects::isNull);
        }

        public boolean isMovePossible(int fromIndex, int toIndex) {
            int min = Math.min(fromIndex, toIndex);
            int max = Math.max(fromIndex, toIndex);
            for (int index = min; index <= max; index++) {
                if (index == fromIndex) {
                    continue;
                }
                if (this.pods[index] != null) {
                    return false;
                }
            }
            return true;
        }

        public boolean isRoomEntrance(int index) {
            return HALLWAY_INDEX_BY_ROOM_INDEX.containsValue(index);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Hallway hallway = (Hallway) o;
            return Arrays.equals(pods, hallway.pods);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(pods);
        }
    }

    record Room(int roomIndex, Amphipod[] pods) {
        public static Room newEmptyRoom(int roomIndex, int roomDepth) {
            return new Room(roomIndex, new Amphipod[roomDepth]);
        }

        @Override
        public Room clone() {
            return new Room(this.roomIndex, this.pods.clone());
        }

        public int getHallwayIndex() {
            return HALLWAY_INDEX_BY_ROOM_INDEX.get(this.roomIndex);
        }

        public Amphipod getPod(int index) {
            return this.pods[index];
        }

        public void addPodAt(int index, Amphipod pod) {
            if (this.pods[index] != null) {
                throw new IllegalStateException("Trying to override pod");
            }
            this.pods[index] = pod;
        }

        public void removePodAt(int index) {
            this.pods[index] = null;
        }

        public boolean isEmpty() {
            return Arrays.stream(this.pods).allMatch(Objects::isNull);
        }

        public Integer getDepthOfPodToMove() {
            if (isValid()) {
                return null;
            }
            for (int index = 0; index < this.pods.length; index++) {
                if (this.pods[index] != null) {
                    return index;
                }
            }
            throw new IllegalStateException("Invalid");
        }

        public int getLastIndexEmpty() {
            for (int index = 0; index < this.pods.length; index++) {
                if (this.pods[index] != null) {
                    return index - 1;
                }
            }
            return this.pods.length - 1;
        }

        public boolean isValid() {
            return Arrays.stream(this.pods)
                    .filter(Objects::nonNull)
                    .allMatch(pod -> pod.targetRoomIndex == this.roomIndex);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Room room = (Room) o;
            return roomIndex == room.roomIndex && Arrays.equals(pods, room.pods);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(roomIndex);
            result = 31 * result + Arrays.hashCode(pods);
            return result;
        }
    }
}
