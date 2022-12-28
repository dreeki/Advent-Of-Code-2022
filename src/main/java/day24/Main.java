package day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Valley valley = readInputPuzzle1();

    valley.findShortestTimes();

    System.out.printf("Puzzle1: %d%n", valley.getShortestTimes().get(0));
    System.out.printf("Puzzle2: %d%n", valley.getShortestTimes().get(2));
  }

  private static Valley readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day24", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    return new Valley(lines);
  }
}
