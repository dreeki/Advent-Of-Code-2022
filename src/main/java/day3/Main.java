package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    List<RuckSack> ruckSacks = readInputPuzzle1();
    int result1 = ruckSacks.stream().mapToInt(RuckSack::findPriority).sum();

    List<RuckSackGroup> ruckSackGroups = readInputPuzzle2();
    int result2 = ruckSackGroups.stream().mapToInt(RuckSackGroup::findPriority).sum();

    System.out.printf("Puzzle1: %d%n", result1);
    System.out.printf("Puzzle2: %d%n", result2);
  }

  private static List<RuckSack> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day3", "puzzle1.txt");
    return Files.lines(path)
        .map(RuckSack::new)
        .toList();
  }

  private static List<RuckSackGroup> readInputPuzzle2() throws IOException {
    Path path = Path.of("src", "main", "resources", "day3", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);
    List<RuckSackGroup> result = new ArrayList<>();
    for(int i = 0; i < lines.size(); i = i+3) {
      result.add(new RuckSackGroup(List.of(lines.get(i), lines.get(i+1), lines.get(i+2))));
    }
    return result;
  }
}
