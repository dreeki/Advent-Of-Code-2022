package day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Crater crater = readInputPuzzle1();

//    System.out.println(crater);

    int initialAmountOfRounds = 10;
    crater.doRounds(initialAmountOfRounds);
    int additionalRounds = crater.keepDoingRounds();

//    System.out.println(crater);

    System.out.printf("Puzzle1: %d%n", crater.countEmptyTilesOfRectangle());
    System.out.printf("Puzzle2: %d%n", initialAmountOfRounds + additionalRounds);
  }

  private static Crater readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day23", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    return new Crater(lines);
  }
}
