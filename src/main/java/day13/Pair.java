package day13;

public record Pair(int index, PacketData firstLine, PacketData secondLine) {

  public Pair(int index, String firstLine, String secondLine) {
    this(index, PacketData.fromLine(firstLine), PacketData.fromLine(secondLine));
  }

  public boolean isRightOrder() {
    return firstLine.isCorrectOrder(secondLine);
  }
}
