package day22;

public record Coordinate(int x, int y) {

  public Coordinate(Coordinate coordinate) {
    this(coordinate.x, coordinate.y);
  }

  Coordinate oneFurtherInDirection(Direction direction) {
    return new Coordinate(x + direction.getDeltaX(), y + direction.getDeltaY());
  }
}
