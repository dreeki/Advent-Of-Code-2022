package day9;

enum Direction {
  R(1, 0),
  L(-1, 0),
  U(0, 1),
  D(0, -1),;

  private final int x;
  private final int y;

  Direction(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
