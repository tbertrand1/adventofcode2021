package fr.t12.adventofcode.common;

public class BinaryUtil {

    private BinaryUtil() {
    }

    public static int binaryToInt(String value) {
        return Integer.parseInt(value, 2);
    }

    public static long binaryToLong(String value) {
        return Long.parseLong(value, 2);
    }
}
