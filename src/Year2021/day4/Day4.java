package Year2021.day4;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day4 {

    public static void main(String[] args) {
        Bingo bingo = getInputFromFile("src\\Year2021\\day4\\input");

        // part 1
        Pair<Integer, List<Integer>> result= playBingo(bingo);
        List <Integer> winningBoard = bingo.getBoards().get( result.getKey());
        List <Integer> winningNumbers =  result.getValue();
        Integer unmarkedBoardSun = getUnmarkedBoardSum(winningBoard,winningNumbers);
        Integer lastWiningNumber = winningNumbers.get(winningNumbers.size() - 1);
        System.out.println(unmarkedBoardSun * lastWiningNumber);




    }

    public static Integer getUnmarkedBoardSum(List<Integer> board, List<Integer> winningNumbers) {
        Optional<Integer> boardSum = board.stream()
                .filter(i -> !winningNumbers.contains(i))
                .reduce(Integer::sum);

        return boardSum.orElse(0);
    }


    public static Pair<Integer, List<Integer>> playBingo(Bingo bingo) {
        List<Integer> winningNumbers = bingo.getWinningNumbers();
        List<Integer> subWinningNumbers;

        boolean won;
        for (int i = 5; i <= bingo.getWinningNumbers().size(); i++) {
            subWinningNumbers = winningNumbers.subList(0, i);

            for (int boardIndex = 0; boardIndex < bingo.getBoards().size(); boardIndex++) {

                won = winningBoard(bingo.getBoards().get(boardIndex), subWinningNumbers);
                if (won) {
                    System.out.println("winning board " + bingo.getBoards().get(boardIndex));
                    System.out.println( "win with " + subWinningNumbers + " boardindex: " + boardIndex);
                    return new Pair<>(boardIndex, subWinningNumbers);
                }
            }

        }
        return null;
    }


    public static boolean winningBoard(List<Integer> board, List<Integer> winningNumbers) {
        int consecutiveMarked = 0;

        // checking rows
        for (int i = 0; i < 25; i += 5) {
            // each rows numbers [0 1 2 3 4] [5 6 7 8 9] etc
            for (int j = i; j < i + 5; j++) {
                if (!winningNumbers.contains(board.get(j))) {
                    break;
                }
                consecutiveMarked++;
                if (consecutiveMarked == 5) {
                    System.out.println("you won by rows");
                    return true;
                }
            }
            consecutiveMarked = 0;
        }

        consecutiveMarked = 0;
        // checking column
        for (int i = 0; i < 5; i++) {
            // each column numbers [0 5 10 15 20] [1 6 11 16 21]
            for (int j = i; j < 25; j += 5) {
                if (!winningNumbers.contains(board.get(j))) {
                    break;
                }
                consecutiveMarked++;
                if (consecutiveMarked == 5) {
                    System.out.println("you won by column");
                    return true;
                }
            }
            consecutiveMarked = 0;
        }


   /*     consecutiveMarked = 0;
        // diagonal [0 6 12 18 24]
        for(int i = 0; i < 25; i+=6) {
            if (!winningNumbers.contains(board.get(i))) {
                break;
            }
            consecutiveMarked++;
            if (consecutiveMarked == 5) {
                System.out.println("you won by diagonal [0 6 12 18 24]");
                return true;
            }
        }

        consecutiveMarked = 0;
        // diagonal [4 8 12 16 20]
        for(int i = 4; i < 21; i+=4) {
            if (!winningNumbers.contains(board.get(i))) {
                System.out.println("not wining");
                break;
            }
            consecutiveMarked++;
            if (consecutiveMarked == 5) {
                System.out.println("you won by diagonal [4 8 12 16 20]");
                return true;
            }
        }*/

        return false;
    }


    public static Bingo getInputFromFile(String fileName) {
        Bingo bingo = new Bingo();
        int lineCount = 0;
        String line;
        List<Integer> board = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            line = br.readLine();
            List<Integer> winningNumbers = Stream
                    .of(line.split(","))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            bingo.setWinningNumbers(winningNumbers);

            // skipping first empty line
            br.readLine();

            while ((line = br.readLine()) != null) {
                lineCount++;
                if (line.isEmpty()) {
                    lineCount = 0;
                    continue;
                }

                board.addAll(
                        Stream.of(line.trim().split("\\s+"))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList())
                );

                if (lineCount == 5) {
                    bingo.addBoard(new ArrayList<>(board));
                    board.clear();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bingo;
    }

}


class Bingo {
    private List<Integer> winningNumbers;
    private List<List<Integer>> boards;

    public Bingo() {
        boards = new ArrayList<>();
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public List<List<Integer>> getBoards() {
        return boards;
    }

    public void setWinningNumbers(List<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    public void setBoards(List<List<Integer>> boards) {
        this.boards = boards;
    }

    public void addBoard(List<Integer> board) {
        this.boards.add(board);
    }

}