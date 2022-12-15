package day15;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public record Coordinate(int x, int y) {
  public int distance(Coordinate coordinate) {
    return Math.abs(x - coordinate.x) + Math.abs(y - coordinate.y);
  }

  public Set<Integer> findXCoordinatesForRowAndDistance(int row, int distance) {
    int xSpan = distance - Math.abs(row - y);

    return IntStream.rangeClosed(0, xSpan).mapToObj(i -> List.of(x - i, x + i)).flatMap(Collection::stream).collect(toSet());
  }
}
