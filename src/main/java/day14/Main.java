package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    List<Path> paths = readInputPuzzle1();

    Cave cave = new Cave(paths);
    cave.dropSand();

    System.out.printf("Puzzle1: %d%n", cave.countSand());
    cave.dropSandToFloor();
    System.out.printf("Puzzle2: %d%n", cave.countSand());
  }

  private static List<Path> readInputPuzzle1() throws IOException {
    java.nio.file.Path path = java.nio.file.Path.of("src", "main", "resources", "day14", "puzzle1.txt");

    return Files.readAllLines(path).stream()
        .map(Main::toPath)
        .toList();
  }

  private static Path toPath(String line) {
    String[] split = line.split(" -> ");
    List<Coordinate> coordinates = Arrays.stream(split)
        .map(part -> part.split(","))
        .map(coords -> new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])))
        .toList();

    return new Path(coordinates);
  }
}
