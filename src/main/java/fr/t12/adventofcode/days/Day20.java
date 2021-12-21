package fr.t12.adventofcode.days;

import fr.t12.adventofcode.common.BinaryUtil;
import fr.t12.adventofcode.common.Tuple;

import java.util.List;
import java.util.function.Function;

public class Day20 extends Day<List<String>, Integer, Integer> {

    private static final int NB_ENHANCEMENTS_PART_1 = 2;
    private static final int NB_ENHANCEMENTS_PART_2 = 50;

    public Day20() {
        super(20);
    }

    @Override
    protected List<String> getInput() {
        return readInputAsItems(Function.identity());
    }

    @Override
    protected Tuple<Integer, Integer> resolvePart1AndPart2(List<String> input) {
        String enhancementAlgorithm = input.get(0);
        boolean[][] inputImage = parseImage(input);
        Image image = new Image(inputImage, 50);
        int part1Result = 0;
        for (int i = 0; i < NB_ENHANCEMENTS_PART_2; i++) {
            if (i == NB_ENHANCEMENTS_PART_1) {
                part1Result = image.countPixelsHit();
            }
            image = image.enhanceImage(enhancementAlgorithm);
        }
        int part2Result = image.countPixelsHit();
        return Tuple.of(part1Result, part2Result);
    }

    private boolean[][] parseImage(List<String> input) {
        boolean[][] image = new boolean[input.size() - 2][input.get(2).length()];
        for (int y = 0; y < input.size() - 2; y++) {
            String line = input.get(y + 2);
            for (int x = 0; x < line.length(); x++) {
                image[y][x] = line.charAt(x) == '#';
            }
        }
        return image;
    }

    static class Image {
        private final boolean[][] pixels;
        private final int width;
        private final int height;

        public Image(boolean[][] inputImage, int nbEnhancement) {
            this.pixels = addMargins(inputImage, nbEnhancement);
            this.width = this.pixels[0].length;
            this.height = this.pixels.length;
        }

        private Image(int width, int height) {
            this.pixels = new boolean[height][width];
            this.width = width;
            this.height = height;
        }

        private static boolean[][] addMargins(boolean[][] inputImage, int nbEnhancement) {
            int marginSize = 2 * nbEnhancement;
            int inputHeight = inputImage.length;
            int inputWidth = inputImage[0].length;
            boolean[][] image = new boolean[inputHeight + marginSize][inputWidth + marginSize];
            for (int y = 0; y < inputHeight; y++) {
                for (int x = 0; x < inputWidth; x++) {
                    image[y + nbEnhancement][x + nbEnhancement] = inputImage[y][x];
                }
            }
            return image;
        }

        public Image enhanceImage(String enhancementAlgorithm) {
            Image enhancedImage = new Image(this.width, this.height);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    String pixelBinary = determineBinaryNumber(x, y);
                    enhancedImage.pixels[y][x] = enhancementAlgorithm.charAt(BinaryUtil.binaryToInt(pixelBinary)) == '#';
                }
            }
            return enhancedImage;
        }

        private String determineBinaryNumber(int x, int y) {
            StringBuilder binary = new StringBuilder();
            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                for (int deltaX = -1; deltaX <= 1; deltaX++) {
                    int pixelX = Math.min(Math.max(deltaX + x, 0), this.width - 1);
                    int pixelY = Math.min(Math.max(deltaY + y, 0), this.height - 1);
                    binary.append(this.pixels[pixelY][pixelX] ? "1" : "0");
                }
            }
            return binary.toString();
        }

        public Integer countPixelsHit() {
            int nbPixelsLit = 0;
            for (boolean[] row : this.pixels) {
                for (boolean pixel : row) {
                    if (pixel) {
                        nbPixelsLit++;
                    }
                }
            }
            return nbPixelsLit;
        }
    }
}
