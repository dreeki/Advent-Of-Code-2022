package day10;

public record NoopOperation() implements Operation {
  @Override
  public void executeOn(CpuState state) {
    state.incrementCycle(1);
  }
}
