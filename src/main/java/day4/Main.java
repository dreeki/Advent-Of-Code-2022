package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    List<Pair> pairs = readInputPuzzle1();
    int result1 = pairs.stream().mapToInt(pair -> pair.isFullyContaining() ? 1 : 0).sum();
    int result2 = pairs.stream().mapToInt(pair -> pair.hasAnyOverlap() ? 1 : 0).sum();

    System.out.printf("Puzzle1: %d%n", result1);
    System.out.printf("Puzzle2: %d%n", result2);
  }

  private static List<Pair> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day4", "puzzle1.txt");
    return Files.lines(path)
        .map(Pair::new)
        .toList();
  }
}
