package day10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

public class CpuState {

  private static final List<Integer> SIGNAL_STRENGTH_MEASURE_TIMES = List.of(20, 60, 100, 140, 180, 220);

  private int cycle;
  private int value;
  private Map<Integer, Integer> signalStrengths;
  private Map<Integer, Integer> signalForEachCycle;

  public CpuState() {
    cycle = 0;
    value = 1;

    signalStrengths = new HashMap<>();
    signalForEachCycle = new HashMap<>();
    signalForEachCycle.put(cycle, value);
  }

  public int getCycle() {
    return cycle;
  }

  public int getValue() {
    return value;
  }

  public void incrementValue(int value) {
    this.value += value;
  }

  public void incrementCycle(int value) {
    this.cycle += value;

    checkSignalStrength();
    setSignalStrengthSprite(value);
  }

  private void setSignalStrengthSprite(int cycleIncrement) {
    if (cycleIncrement == 2){
      signalForEachCycle.put(cycle-1, value);
    }
    signalForEachCycle.put(cycle, value);
  }

  private void checkSignalStrength() {
    findClosestPastTime().ifPresent(time -> signalStrengths.put(time, time*value));
  }

  private OptionalInt findClosestPastTime() {
    return SIGNAL_STRENGTH_MEASURE_TIMES.stream()
        .filter(val -> val <= cycle)
        .filter(val -> !signalStrengths.containsKey(val))
        .mapToInt(i -> i)
        .min();
  }

  public Map<Integer, Integer> getSignalStrengths() {
    return signalStrengths;
  }

  public char getCharForCycle(int cycle) {
    int spriteCenter = signalForEachCycle.get(cycle);

    int cyclePos = cycle;
    while(cyclePos > 40) {
      cyclePos -= 40;
    }

    if(Math.abs(cyclePos-1 - spriteCenter) <= 1) {
      return '#';
    }else {
      return '.';
    }
  }
}
