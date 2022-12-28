package day18;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Box {
  private final List<Cube> lavaCubes;
  private final List<Cube> grid;

  private final int minX;
  private final int maxX;
  private final int minY;
  private final int maxY;
  private final int minZ;
  private final int maxZ;

  public Box(List<Cube> lavaCubes) {
    this.lavaCubes = new ArrayList<>(lavaCubes);
    this.grid = new ArrayList<>(lavaCubes);

    minX = lavaCubes.stream().mapToInt(Cube::x).min().getAsInt();
    maxX = lavaCubes.stream().mapToInt(Cube::x).max().getAsInt();
    minY = lavaCubes.stream().mapToInt(Cube::y).min().getAsInt();
    maxY = lavaCubes.stream().mapToInt(Cube::y).max().getAsInt();
    minZ = lavaCubes.stream().mapToInt(Cube::z).min().getAsInt();
    maxZ = lavaCubes.stream().mapToInt(Cube::z).max().getAsInt();
  }

  public int calculateSurfaceArea() {
    int amountOfTouches = 0;
    for (int i = 0; i < lavaCubes.size() - 1; i++) {
      for (int j = i + 1; j < lavaCubes.size(); j++) {
        if (lavaCubes.get(i).touches(lavaCubes.get(j))) {
          amountOfTouches++;
        }
      }
    }

    return lavaCubes.size() * 6 - amountOfTouches * 2;
  }

  public int calculateExternalSurfaceArea() {
    int result = 0;
    Queue<Cube> queue = new LinkedList<>();
    queue.add(new Cube(minX - 1, minY - 1, minZ - 1));

    while (!queue.isEmpty()) {
      Cube current = queue.poll();

      if(isInGridBoundaries(current) && !isInGrid(current)) {
        grid.add(current);

        result += lavaCubes.stream().filter(lavacube -> lavacube.touches(current)).count();

        queue.add(current.up());
        queue.add(current.down());
        queue.add(current.right());
        queue.add(current.left());
        queue.add(current.front());
        queue.add(current.back());
      }
    }

    return result;
  }

  private boolean isInGrid(Cube cube) {
    return grid.contains(cube);
  }

  private boolean isInGridBoundaries(Cube cube) {
    return cube.x() >= minX - 1
        && cube.y() >= minY - 1
        && cube.z() >= minZ - 1
        && cube.x() <= maxX + 1
        && cube.y() <= maxY + 1
        && cube.z() <= maxZ + 1;
  }
}
