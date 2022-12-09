package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Forest forest = readInputPuzzle1();
    int result1 = forest.findAmountOfVisibleTrees();

    System.out.printf("Puzzle1: %d%n", result1);
    System.out.printf("Puzzle2: %d%n", forest.getHeightestScenicScore());
  }

  private static Forest readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day8", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    return new Forest(lines);
  }
}
