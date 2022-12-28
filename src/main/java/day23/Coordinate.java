package day23;

public record Coordinate(int x, int y) {
  public Coordinate fromDeltaCoordinate(DeltaCoordinate deltaCoordinate) {
    return new Coordinate(x + deltaCoordinate.getDeltaX(), y + deltaCoordinate.getDeltaY());
  }
}
