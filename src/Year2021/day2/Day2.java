package Year2021.day2;

import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day2 {

    public static void main(String[] args) {
        // part 1
        List<Pair<String, Integer>> actionList = getInputFromFile("src\\Year2021\\day2\\input");
        Submarine sub = new Submarine();
        actionList.forEach(sub::move);

        System.out.println("Submarine");
        System.out.println("depth: " + sub.getDepth() + " horizontalPosition: " + sub.getHorizontalPosition());
        System.out.println("depth * horizontalPosition: " + sub.getDepth()* sub.getHorizontalPosition());


        // part 2
        Submarine improvedSub = new ImprovedSubmarine();
        actionList.forEach(improvedSub::move);

        System.out.println("ImprovedSubmarine");
        System.out.println(
                "depth: " + improvedSub.getDepth()
                        + " horizontalPosition: "
                        + improvedSub.getHorizontalPosition());
        System.out.println("depth * horizontalPosition: "
                + improvedSub.getDepth()* improvedSub.getHorizontalPosition());
    }

    public static List<Pair<String, Integer>> getInputFromFile(String fileName) {
        List<Pair<String, Integer>> inputs = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            inputs = lines
                    .map(Day2::createAction)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputs;
    }

    public  static Pair<String, Integer> createAction(String actionStr) {
        String [] actionSplit = actionStr.split(" ");
        return new Pair<>(actionSplit[0], Integer.parseInt(actionSplit[1]));
    }
}

class Submarine {
    protected int horizontalPosition;
    protected int depth;

    public Submarine() {
        this(0,0);
    }

    public Submarine(int horizontalPosition, int depth) {
        this.horizontalPosition = horizontalPosition;
        this.depth = depth;
    }

    public void move(Pair<String, Integer> action) {
        switch (action.getKey()) {
            case "forward":
                horizontalPosition += action.getValue();
                break;
            case "down":
                depth += action.getValue();
                break;
            case  "up":
                depth -= action.getValue();
        }
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public int getDepth() {
        return depth;
    }
}

class ImprovedSubmarine extends Submarine {

    private int aim;

    public ImprovedSubmarine() {
        this(0, 0);
    }

    public ImprovedSubmarine(int horizontalPosition, int depth) {
        super(horizontalPosition, depth);
        this.aim = 0;
    }

    @Override
    public void move(Pair<String, Integer> action) {
        switch (action.getKey()) {
            case "forward":
                horizontalPosition += action.getValue();
                depth += aim * action.getValue();
                break;
            case "down":
                aim += action.getValue();
                break;
            case  "up":
                aim -= action.getValue();
        }
    }
}
