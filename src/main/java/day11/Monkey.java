package day11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Monkey {
  private final Queue<Long> itemWorryLevels;
  private final char operationOperator;
  private final String operationValue;
  private final int testDivision;
  private final int testSuccessTarget;
  private final int testFailTarget;

  private int amountOfInspections;

  public Monkey(List<String> lines) {
    itemWorryLevels = new LinkedList<>();
    String[] items = lines.get(1).substring("  Starting items: ".length()).split(", ");
    Arrays.stream(items).mapToLong(Long::parseLong).forEach(itemWorryLevels::add);

    String[] operation = lines.get(2).substring("  Operation: new = old ".length()).split(" ");
    operationOperator = operation[0].charAt(0);
    operationValue = operation[1];

    testDivision = Integer.parseInt(lines.get(3).substring("  Test: divisible by ".length()));
    testSuccessTarget = Integer.parseInt(lines.get(4).substring("    If true: throw to monkey ".length()));
    testFailTarget = Integer.parseInt(lines.get(5).substring("    If false: throw to monkey ".length()));

    amountOfInspections = 0;
  }

  public List<Action> doRoundActions(int lcm) {
    amountOfInspections += itemWorryLevels.size();

    List<Action> actions = itemWorryLevels.stream().map(worryLevel -> executeOnItem(worryLevel, lcm)).toList();
    itemWorryLevels.clear();
    return actions;
  }

  private Action executeOnItem(long worryLevel, int lcm) {
    long newWorryLevel = calculateNewWorryLevel(worryLevel, lcm);
    int targetMonkey = findTargetMonkey(newWorryLevel);

    return new Action(targetMonkey, newWorryLevel);
  }

  private int findTargetMonkey(long worryLevel) {
    return worryLevel % testDivision == 0 ? testSuccessTarget : testFailTarget;
  }

  private long calculateNewWorryLevel(long worryLevel, int lcm) {
    long value;
    if (operationOperator == '+') {
      value = doSum(worryLevel, findRightSideValue(worryLevel));
    } else {
      value = doMultiplication(worryLevel, findRightSideValue(worryLevel));
    }
    if(lcm == 3) {
      return value / 3;
    }else {
      return value % lcm;
    }
  }

  private long doMultiplication(long first, long second) {
    return first * second;
  }

  private long doSum(long first, long second) {
    return first + second;
  }

  private long findRightSideValue(long worryLevel) {
    if(operationValue.equals("old")) {
      return worryLevel;
    }
    return Integer.parseInt(operationValue);
  }

  public int getAmountOfInspections() {
    return amountOfInspections;
  }

  public void addItem(long worryValue) {
    itemWorryLevels.add(worryValue);
  }

  public int getTestDivision() {
    return testDivision;
  }
}
