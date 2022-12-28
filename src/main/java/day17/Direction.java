package day17;

public enum Direction {
  LEFT(-1),
  RIGHT(1);

  private final int deltaX;

  Direction(int deltaX) {
    this.deltaX = deltaX;
  }

  public int getDeltaX() {
    return deltaX;
  }

  public static Direction fromChar(char c) {
    return switch(c) {
      case '<' -> LEFT;
      case '>' -> RIGHT;
      default -> throw new IllegalArgumentException();
    };
  }
}
