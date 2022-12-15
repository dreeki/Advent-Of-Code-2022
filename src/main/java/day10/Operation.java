package day10;

import java.util.OptionalInt;

sealed interface Operation permits NoopOperation, AddOperation {
  void executeOn(CpuState state);

  static Operation createFrom(String line) {
    String[] splitted = line.split(" ");
    if(splitted.length == 1) {
      return new NoopOperation();
    }else {
      return new AddOperation(Integer.parseInt(splitted[1]));
    }
  }
}
