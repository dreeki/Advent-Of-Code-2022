package day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  private static final int TOTAL_TIME_PART_ONE = 24;
  private static final int TOTAL_TIME_PART_TWO = 32;

  public static void main(String[] args) throws IOException {
    List<Factory> factoriesPart1 = readInputPuzzle1();
    factoriesPart1.forEach(Factory::findMaxAmountOfGeodes);
    int result1 = factoriesPart1.stream().mapToInt(f -> f.getMaxGeodes() * f.getId()).sum();
    System.out.printf("Puzzle1: %d%n", result1);

    // PART 2 gives a wrong answer for blueprint 1 of example, but works for my input... :(
    List<Factory> factoriesPart2 = readInputPuzzle2();
    factoriesPart2.forEach(Factory::findMaxAmountOfGeodes);
    int result2 = factoriesPart2.stream().mapToInt(Factory::getMaxGeodes).reduce(1, (a, b) -> a * b);
    System.out.printf("Puzzle2: %d%n", result2);
  }

  private static List<Factory> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day19", "puzzle1.txt");
    return Files.lines(path)
        .map(Blueprint::new)
        .map(blueprint -> new Factory(blueprint, TOTAL_TIME_PART_ONE))
        .toList();
  }

  private static List<Factory> readInputPuzzle2() throws IOException {
    Path path = Path.of("src", "main", "resources", "day19", "puzzle1.txt");
    return Files.lines(path)
        .limit(3)
        .map(Blueprint::new)
        .map(blueprint -> new Factory(blueprint, TOTAL_TIME_PART_TWO))
        .toList();
  }
}
