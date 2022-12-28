package day25;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SnafuNumber {
  private static final List<Long> POWERS_OF_FIVE = new ArrayList<>(List.of(1L));
  private final List<Integer> multiplicatorsOfPowersOfFive;

  public SnafuNumber(String line) {
    multiplicatorsOfPowersOfFive = new ArrayList<>(Arrays.stream(line.split("")).map(s -> s.charAt(0)).map(this::snafuToInteger).toList());
    Collections.reverse(multiplicatorsOfPowersOfFive);

    if(POWERS_OF_FIVE.size() < multiplicatorsOfPowersOfFive.size()) {
      for(int i = POWERS_OF_FIVE.size(); i < multiplicatorsOfPowersOfFive.size(); i++) {
        POWERS_OF_FIVE.add(POWERS_OF_FIVE.get(POWERS_OF_FIVE.size()-1) *5);
      }
    }
  }

  public SnafuNumber(List<Integer> multiplicatorsOfPowersOfFive) {
    this.multiplicatorsOfPowersOfFive = multiplicatorsOfPowersOfFive;
  }

  public static SnafuNumber fromDecimal(long decimal) {
    while (POWERS_OF_FIVE.get(POWERS_OF_FIVE.size()-1) < decimal) {
      POWERS_OF_FIVE.add(POWERS_OF_FIVE.get(POWERS_OF_FIVE.size()-1) *5);
    }

    List<Integer> result = new ArrayList<>();

    long partToDo = decimal;

    PowerAndValue firstPowerOfFive = findFirstPowerOfFive(decimal);
    result.add(firstPowerOfFive.value);

    partToDo -= POWERS_OF_FIVE.get(firstPowerOfFive.power) * firstPowerOfFive.value;
    int currentPower = firstPowerOfFive.power - 1;

    while (currentPower >= 0) {
      int valueForPower = findValueForPower(partToDo, currentPower);
      result.add(valueForPower);

      partToDo -= POWERS_OF_FIVE.get(currentPower) * valueForPower;
      currentPower--;
    }

    Collections.reverse(result);

    return new SnafuNumber(result);
  }

  private static int findValueForPower(long decimal, int power) {
    long maxWithFirstAsMinus2 = findMaxValueForPowerAndFirstValue(power, -2);
    if(decimal <= maxWithFirstAsMinus2) {
      return -2;
    }

    long maxWithFirstAsMinus1 = findMaxValueForPowerAndFirstValue(power, -1);
    if(decimal <= maxWithFirstAsMinus1) {
      return -1;
    }

    long maxWithFirstAsZero = findMaxValueForPowerAndFirstValue(power, 0);
    if(decimal <= maxWithFirstAsZero) {
      return 0;
    }

    long maxWithFirstAsOne = findMaxValueForPowerAndFirstValue(power, 1);
    if(decimal <= maxWithFirstAsOne) {
      return 1;
    }

    long maxWithFirstAsTwo = findMaxValueForPowerAndFirstValue(power, 2);
    if(decimal <= maxWithFirstAsTwo) {
      return 2;
    }

    throw new IllegalArgumentException();
  }

  private static PowerAndValue findFirstPowerOfFive(long decimal) {
    if(decimal <= 2) {
      return new PowerAndValue(0, (int) decimal);
    }

    for(int i = 1; i < POWERS_OF_FIVE.size(); i++) {
      long maxWithFirstAsOne = findMaxValueForPowerAndFirstValue(i, 1);
      if(maxWithFirstAsOne >= decimal) {
        return new PowerAndValue(i, 1);
      }

      long maxWithFirstAsTwo = findMaxValueForPowerAndFirstValue(i, 2);
      if(maxWithFirstAsTwo >= decimal) {
        return new PowerAndValue(i, 2);
      }

    }
    throw new IllegalArgumentException();
  }

  private static long findMaxValueForPowerAndFirstValue(int power, int firstValue) {
    return POWERS_OF_FIVE.get(power) * firstValue + IntStream.range(0, power).mapToLong(val -> POWERS_OF_FIVE.get(val) * 2).sum();
  }

  public long calculateDecimalValue() {
    return IntStream.range(0, multiplicatorsOfPowersOfFive.size()).mapToLong(i -> POWERS_OF_FIVE.get(i) * multiplicatorsOfPowersOfFive.get(i)).sum();
  }

  private int snafuToInteger(char c) {
    return switch (c) {
      case '=' -> -2;
      case '-' -> -1;
      case '0' -> 0;
      case '1' -> 1;
      case '2' -> 2;
      default -> throw new IllegalArgumentException();
    };
  }

  private char integerToSnafu(int i) {
    return switch (i) {
      case -2 -> '=';
      case -1 -> '-';
      case 0 -> '0';
      case 1 -> '1';
      case 2 -> '2';
      default -> throw new IllegalArgumentException();
    };
  }

  @Override
  public String toString() {
    List<Integer> temp = new ArrayList<>(multiplicatorsOfPowersOfFive);
    Collections.reverse(temp);
    return temp.stream().map(this::integerToSnafu).map(c -> c + "").collect(joining(""));
  }

  private record PowerAndValue(int power, int value){ }
}
