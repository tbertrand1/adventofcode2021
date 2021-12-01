package fr.t12.adventofcode.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileUtil {
    private static final String RESOURCES_FOLDER = "src/main/resources/";

    private FileUtil() {
    }

    public static String readFile(String filename) {
        try {
            return FileUtils.readFileToString(new File(RESOURCES_FOLDER, filename), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("File not found", e);
        }
    }

    public static List<String> readFileLines(String filename) {
        try {
            return FileUtils.readLines(new File(RESOURCES_FOLDER, filename), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("File not found", e);
        }
    }
}
