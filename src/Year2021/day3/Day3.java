package Year2021.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day3 {

    public static void main(String[] args) {
        List<String> diagnostics = getInputFromFile("src\\Year2021\\day3\\input");

        // part 1
        int[] gammaRate = getGammaRate(diagnostics);
        int[] epsilonRate = flipBitArr(gammaRate);

        System.out.println(IntValueOf(epsilonRate) * IntValueOf(gammaRate));


        // part 2
        int oxygenGeneratorRating = getRating(diagnostics, (b) -> b ? '1' : '0');
        int CO2ScrubberRating = getRating(diagnostics, (b) -> b ? '0' : '1');
        System.out.println(oxygenGeneratorRating * CO2ScrubberRating);
    }


    public static int getRating(List<String> inputs, Function<Boolean, Character> getCharByCommonType) {
        List<String> diagnostics = new ArrayList<>(inputs);

        for (int index = 0; index < inputs.get(0).length(); index++) {
            int[] rating = flatDiagnosticList(diagnostics);
            boolean common = diagnostics.size() - rating[index] <= rating[index];
            Character value = getCharByCommonType.apply(common);

            diagnostics = filterByIndex(diagnostics, index, value);

            if (diagnostics.size() == 1) {
                break;
            }
        }

        return Integer.parseInt(diagnostics.get(0), 2);
    }

    public static List<String> filterByIndex(List<String> diagnostics, int index, char value) {
        return diagnostics
                .stream()
                .filter(a -> a.charAt(index) == value)
                .collect(Collectors.toList());
    }

    public static int IntValueOf(int[] intBitArr) {
        String bitString = Arrays.toString(intBitArr).replaceAll("\\[|\\]|,|\\s", "");

        return Integer.parseInt(bitString, 2);
    }

    public static int[] flipBitArr(int[] bitArr) {
        return Arrays.stream(bitArr)
                .map(x -> x == 1 ? 0 : 1)
                .toArray();
    }

    public static int[] getGammaRate(List<String> diagnostics) {
        int[] output = flatDiagnosticList(diagnostics);

        for (int i = 0; i < output.length; i++) {
            output[i] = diagnostics.size() - output[i] < output[i] ? 1 : 0;
        }
        return output;
    }

    public static int[] flatDiagnosticList(List<String> diagnostics) {
        int[] output = new int[]{};

        for (String diagnostic : diagnostics) {
            output = reduce(output, diagnostic);
        }

        return output;
    }

    public static int[] reduce(int[] prev, String curr) {
        if (prev.length == 0) {
            prev = new int[curr.length()];
        }

        int[] currArr = Stream.of(curr.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i = 0; i < prev.length; i++) {
            prev[i] += currArr[i];
        }

        return prev;
    }

    public static List<String> getInputFromFile(String fileName) {
        List<String> input = new ArrayList<>();

        try {
            input = Files.readAllLines(Paths.get(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

}
