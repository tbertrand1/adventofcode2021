package fr.t12.adventofcode;

public class SimulationsRunner {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (var clazz : DaysRunner.DAYS_CLASSES) {
            try {
                clazz.getDeclaredConstructor().newInstance().simulation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.printf("\nTotal time: %d ms\n", System.currentTimeMillis() - start);
    }
}
