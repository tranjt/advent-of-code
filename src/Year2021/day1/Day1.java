package Year2021.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Day1 {

    public static void main(String[] args) {
        List<Integer> measurements = getInputFromFile("src\\Year2021\\day1\\input");
        // part 1
        largerThanPreviousCount(measurements);


        // part 2
        List<Integer> threeMeasurements = getWindowedList(
                measurements
                        .stream()
                        .mapToInt(Integer::intValue)
                        .toArray(),
                3);

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
        long count = IntStream
                .rangeClosed(1, measurements.size() - 1)
                .filter(i -> measurements.get(i) > measurements.get(i - 1))
                .count();

        System.out.println("There are " + count + " measurements that are larger than the previous.");
        return (int) count;
    }

    public static List<Integer> getWindowedList(int[] arr, int windowSize) {
        int windowSum = 0;
        List<Integer> windowedList = new ArrayList<>();

        if (arr.length < windowSize) {
            return windowedList;
        }

        for (int i = 0; i < windowSize; i++) {
            windowSum += arr[i];
        }
        windowedList.add(windowSum);

        for (int end = windowSize; end < arr.length; end++) {
            windowSum += arr[end] - arr[end - windowSize];
            windowedList.add(windowSum);
        }

        return windowedList;
    }

}
