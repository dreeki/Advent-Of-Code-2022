package day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Box box = readInputPuzzle1();

    System.out.printf("Puzzle1: %d%n", box.calculateSurfaceArea());
    System.out.printf("Puzzle2: %d%n", box.calculateExternalSurfaceArea());
  }

  private static Box readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day18", "puzzle1.txt");
    List<Cube> cubes = Files.lines(path)
        .map(line -> line.split(","))
        .map(splitted -> new Cube(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2])))
        .toList();

    return new Box(cubes);
  }
}
