package day18;

public record Cube(int x, int y, int z) {

  public Cube left() {
    return new Cube(x - 1, y, z);
  }

  public Cube right() {
    return new Cube(x + 1, y, z);
  }

  public Cube up() {
    return new Cube(x, y + 1, z);
  }

  public Cube down() {
    return new Cube(x, y - 1, z);
  }

  public Cube front() {
    return new Cube(x, y, z - 1);
  }

  public Cube back() {
    return new Cube(x, y, z + 1);
  }

  public boolean touches(Cube cube) {
    int xDiff = Math.abs(x - cube.x);
    int yDiff = Math.abs(y - cube.y);
    int zDiff = Math.abs(z - cube.z);
    return (xDiff + yDiff + zDiff) == 1;
  }
}
