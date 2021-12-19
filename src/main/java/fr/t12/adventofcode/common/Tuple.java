package fr.t12.adventofcode.common;

public record Tuple<T1, T2>(T1 first, T2 second) {
    public static <T1, T2> Tuple<T1, T2> empty() {
        return new Tuple<>(null, null);
    }

    public static <T1, T2> Tuple<T1, T2> of(T1 first, T2 second) {
        return new Tuple<>(first, second);
    }

    public boolean isEmpty() {
        return first == null || second == null;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
