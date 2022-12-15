package day10;

public record AddOperation(int value) implements Operation {
  @Override
  public void executeOn(CpuState state) {
    state.incrementCycle(2);
    state.incrementValue(value);
  }
}
