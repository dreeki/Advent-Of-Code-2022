package day17;

public record Position(long x, long y) {
  public Position fromDirection(Direction direction) {
    return new Position(x + direction.getDeltaX(), y);
  }

  public Position drop() {
    return new Position(x, y - 1);
  }
}
