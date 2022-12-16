package day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Cave cave = readInputPuzzle1();
    cave.openValvesSolo();

    System.out.printf("Puzzle1: %d%n", cave.calculateMaximumPressureReleaseAlone());

    cave.openValvesDuo();
    // this takes several minutes to calculate...
    System.out.printf("Puzzle2: %d%n", cave.calculateMaximumPressureReleaseWithElephant());
  }

  private static Cave readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day16", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    return new Cave(lines);
  }
}
