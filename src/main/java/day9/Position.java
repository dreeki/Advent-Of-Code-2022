package day9;

record Position(int x, int y) {
  public Position move(Direction direction) {
    return new Position(x + direction.getX(), y + direction.getY());
  }

  public boolean isAdjacent(Position position) {
    return Math.abs(x - position.x) <= 1 && Math.abs(y - position.y) <= 1;
  }
}
