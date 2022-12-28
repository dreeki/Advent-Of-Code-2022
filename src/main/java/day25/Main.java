package day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    List<SnafuNumber> snafuNumbers = readInputPuzzle1();

    long sum = snafuNumbers.stream().mapToLong(SnafuNumber::calculateDecimalValue).sum();

    SnafuNumber snafuSum = SnafuNumber.fromDecimal(sum);
    System.out.printf("Puzzle1: %s%n", snafuSum);
    System.out.printf("Puzzle2: %d%n", 0);
  }

  private static List<SnafuNumber> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day25", "puzzle1.txt");
    return Files.lines(path)
        .map(SnafuNumber::new)
        .toList();
  }
}
