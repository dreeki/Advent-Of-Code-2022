package day10;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) throws IOException {
    CpuState cpuState = new CpuState();
    List<Operation> operations = readInputPuzzle1();

    operations.forEach(operation -> operation.executeOn(cpuState));
    int result1 = cpuState.getSignalStrengths().values().stream().mapToInt(i -> i).sum();

    String result2 = IntStream.of(0, 1, 2, 3, 4, 5)
        .mapToObj(i -> IntStream.rangeClosed((i * 40) + 1, ((i + 1) * 40)).mapToObj(cycle -> cpuState.getCharForCycle(cycle) + "").collect(joining()))
        .collect(joining("\n"));


    System.out.printf("Puzzle1: %d%n", result1);
    System.out.printf("Puzzle2: %n%s%n", result2);
  }

  private static List<Operation> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day10", "puzzle1.txt");
    return Files.lines(path)
        .map(Operation::createFrom)
        .toList();
  }
}
