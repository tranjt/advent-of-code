package Year2021.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class day1 {

    public static void main(String[] args) {
        List<Integer> measurements = getInputFromFile("src\\Year2021\\day1\\input");
        // part 1
        largerThanPreviousCount(measurements);

        // part 2
        List<Integer> threeMeasurements = getThreeMeasurementList(measurements);

        largerThanPreviousCount(threeMeasurements);
    }

    public static List<Integer> getInputFromFile(String fileName) {
        List<Integer> input = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            input = lines
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static int largerThanPreviousCount(List<Integer> measurements) {
        AtomicInteger count = new AtomicInteger();

        measurements.stream()
                .reduce((prev, curr) -> {
                    if (prev < curr) {
                        count.getAndIncrement();
                    }
                    return curr;
                });

        System.out.println("There are " + count + " measurements that are larger than the previous. ");
        return count.intValue();
    }

    public static List<Integer> getThreeMeasurementList(List<Integer> measurements) {
        List<Integer> threeMeasurement = new ArrayList<>();

        if (measurements.size() < 3) {
            return threeMeasurement;
        }

        Integer prevprev = measurements.get(0);
        Integer prev = measurements.get(1);
        Integer curr;

        for (int i = 2; i <= measurements.size() - 1; i++) {
            curr = measurements.get(i);
            threeMeasurement.add(prevprev + prev + curr);
            prevprev = prev;
            prev = curr;
        }

        return threeMeasurement;
    }

}
