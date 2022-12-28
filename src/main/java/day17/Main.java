package day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Tower tower = readInputPuzzle1();
    System.out.printf("Puzzle1: %d%n", tower.dropRocks(2022));
    System.out.printf("Puzzle2: %d%n", tower.dropRocks(1000000000000L));
  }

  private static Tower readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day17", "puzzle1.txt");
    List<String> strings = Files.readAllLines(path);

    return new Tower(strings.get(0));
  }
}
