package day24;

public enum Direction {
  UP(0, -1),
  DOWN(0, 1),
  LEFT(-1, 0),
  RIGHT(1, 0);

  private final int deltaX;
  private final int deltaY;

  Direction(int deltaX, int deltaY) {
    this.deltaX = deltaX;
    this.deltaY = deltaY;
  }

  public static Direction fromChar(char c) {
    return switch (c) {
      case '^' -> UP;
      case 'v' -> DOWN;
      case '<' -> LEFT;
      case '>' -> RIGHT;
      default -> throw new IllegalArgumentException();
    };
  }

  public int getDeltaX() {
    return deltaX;
  }

  public int getDeltaY() {
    return deltaY;
  }

  @Override
  public String toString() {
    return switch (this) {
      case UP -> "^";
      case DOWN -> "v";
      case LEFT -> "<";
      case RIGHT -> ">";
    };
  }
}
