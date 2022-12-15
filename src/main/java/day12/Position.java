package day12;

import java.util.Objects;

final class Position {
  private final int x;
  private final int y;
  private final char code;

  private int shortestDistance;

  Position(int x, int y, char code) {
    this.x = x;
    this.y = y;
    this.code = code;
    shortestDistance = Integer.MAX_VALUE;
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  private int elevation() {
    if (code == 'S') {
      return 1;
    }
    if (code == 'E') {
      return 'z' - 'a' + 1;
    }
    return code - 'a' + 1;
  }

  public boolean isPossibleNext(Position position) {
    return elevation() + 1 >= position.elevation();
  }

  public boolean isStart() {
    return code == 'S';
  }

  public boolean isA() {
    return code == 'a' || isStart();
  }

  public boolean isEnd() {
    return code == 'E';
  }

  public static Position createBorder(int x, int y) {
    Position dummy = new Position(x, y, (char) 1);
    dummy.setShortestDistance(Integer.MIN_VALUE);
    return dummy;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Position) obj;
    return this.x == that.x &&
        this.y == that.y &&
        this.code == that.code;
  }

  public int getShortestDistance() {
    return shortestDistance;
  }

  public void setShortestDistance(int shortestDistance) {
    this.shortestDistance = shortestDistance;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, code);
  }

  @Override
  public String toString() {
    return "Position{" +
        "x=" + x +
        ", y=" + y +
        ", code=" + code +
        ", shortestDistance=" + shortestDistance +
        '}';
  }
}
