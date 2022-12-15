package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    List<Monkey> monkeysPart1 = readInputPuzzle1();

    for(int i = 0; i < 20; i++) {
      for(Monkey monkey : monkeysPart1) {
        List<Action> actions = monkey.doRoundActions(3);
        actions.forEach(action -> monkeysPart1.get(action.targetMonkey()).addItem(action.worryValue()));
      }
    }

    int result1 = monkeysPart1.stream().map(Monkey::getAmountOfInspections).sorted(Comparator.reverseOrder()).limit(2).mapToInt(i -> i).reduce(1,  (a, b) -> a * b);

    System.out.printf("Puzzle1: %d%n", result1);

    List<Monkey> monkeysPart2 = readInputPuzzle1();

    int lcm = calculateLcm(monkeysPart2.stream().map(Monkey::getTestDivision).toList());
    for(int i = 0; i < 10000; i++) {
      for(Monkey monkey : monkeysPart2) {
        List<Action> actions = monkey.doRoundActions(lcm);
        actions.forEach(action -> monkeysPart2.get(action.targetMonkey()).addItem(action.worryValue()));
      }
    }
    long result2 = monkeysPart2.stream().map(Monkey::getAmountOfInspections).sorted(Comparator.reverseOrder()).limit(2).mapToLong(i -> i).reduce(1,  (a, b) -> a * b);

    System.out.printf("Puzzle2: %d%n", result2);
  }

  private static int calculateLcm(List<Integer> values) {
    int result = 1;

    for (Integer value : values) {
      int gcd = findGcd(value, result);
      result = (result * value) / gcd;

    }

    return result;
  }

  private static int findGcd(int a, int b) {
    if(b == 0) {
      return a;
    }
    return findGcd(b, a%b);
  }

  private static List<Monkey> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day11", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    List<Monkey> result = new ArrayList<>();
    for (int i = 0; i < lines.size(); i += 7) {
      result.add(new Monkey(List.of(
          lines.get(i),
          lines.get(i+1),
          lines.get(i+2),
          lines.get(i+3),
          lines.get(i+4),
          lines.get(i+5)
      )));
    }

    return result;
  }
}
