package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Hill hill = readInputPuzzle1();
//    hill.doDijkstra(); // part 1 solution
    hill.doDijkstraReverse();

//    System.out.printf("Puzzle1: %d%n", hill.getShortestDistanceToEnd()); // part 1 solution
    System.out.printf("Puzzle1: %d%n", hill.getShortestDistanceToStart());
    System.out.printf("Puzzle2: %d%n", hill.getShortestDistanceToA());
  }

  private static Hill readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day12", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    return new Hill(lines);
  }
}
