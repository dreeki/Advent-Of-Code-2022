package day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Cave cave = readInputPuzzle1();

    System.out.printf("Puzzle1: %d%n", cave.countEmptySpotsAtRow(2000000));

    Coordinate coordinate = cave.findDistressBeacon();
    System.out.printf("Puzzle2: %d%n", (long) coordinate.x() * 4000000 + coordinate.y());
  }

  private static Cave readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day15", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    return new Cave(lines);
  }
}
