package day14;

import static day14.Content.ROCK;
import static day14.Content.SAND;
import static day14.Content.SOURCE;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Cave {
  private static final Coordinate SOURCE_COORDINATE = new Coordinate(500, 0);

  private final int minX;
  private final int maxX;
  private final int maxY;

  private final Map<Coordinate, Content> locations;
  // houdt alle locaties bij
  // kent hoogte en breedge grenzen
  // indien zand de laagste pos bereikt of links rechts buiten kader -> freedom!


  public Cave(List<Path> rockPaths) {
    locations = new HashMap<>();
    locations.put(SOURCE_COORDINATE, SOURCE);

    rockPaths.stream()
        .map(Path::coordinates)
        .flatMap(Collection::stream)
        .forEach(coordinate -> locations.put(coordinate, ROCK));

    Set<Coordinate> coordinates = locations.keySet();

    minX = coordinates.stream().mapToInt(Coordinate::x).min().getAsInt();
    maxX = coordinates.stream().mapToInt(Coordinate::x).max().getAsInt();
    maxY = coordinates.stream().mapToInt(Coordinate::y).max().getAsInt();
    addFloor();
  }

  private void addFloor() {
    int floorY = maxY + 2;
    Path floor = new Path(List.of(new Coordinate(SOURCE_COORDINATE.x() - (floorY + 5), floorY), new Coordinate(SOURCE_COORDINATE.x() + (floorY + 5), floorY)));

    floor.coordinates().forEach(coordinate -> locations.put(coordinate, ROCK));
  }

  public void dropSand() {
    Coordinate nextSandCoordinate = findNextSandCoordinate();

    while(!isFinish(nextSandCoordinate)) {
      locations.put(nextSandCoordinate, SAND);

      nextSandCoordinate = findNextSandCoordinate();
    }
  }

  public void dropSandToFloor() {
    Coordinate nextSandCoordinate = findNextSandCoordinateToFloor();

    while(!isFull()) {
      locations.put(nextSandCoordinate, SAND);

      nextSandCoordinate = findNextSandCoordinateToFloor();
    }
  }

  public long countSand() {
    return locations.values().stream().filter(content -> content.equals(SAND)).count();
  }

  private Coordinate findNextSandCoordinateToFloor() {
    Coordinate next = SOURCE_COORDINATE;
    Optional<Coordinate> optionalNext = findNextSandCoordinate(next);
    while (optionalNext.isPresent()) {
      if (isFull()){
        break;
      }
      next = optionalNext.get();
      optionalNext = findNextSandCoordinate(next);
    }
    return next;
  }

  private Coordinate findNextSandCoordinate() {
    Coordinate next = SOURCE_COORDINATE;
    Optional<Coordinate> optionalNext = findNextSandCoordinate(next);
    while (optionalNext.isPresent()) {
      if (isFinish(next)){
        break;
      }
      next = optionalNext.get();
      optionalNext = findNextSandCoordinate(next);
    }
    return next;
  }

  private Optional<Coordinate> findNextSandCoordinate(Coordinate coordinate) {
    Coordinate next = new Coordinate(coordinate.x(), coordinate.y() + 1);
    if(locations.containsKey(next)) {
      Coordinate left = new Coordinate(next.x() - 1, next.y());
      if(locations.containsKey(left)) {
        Coordinate right = new Coordinate(next.x() + 1, next.y());
        if(locations.containsKey(right)){
          return Optional.empty();
        }
        return Optional.of(right);
      }
      return Optional.of(left);
    }
    return Optional.of(next);
  }

  private boolean isFull() {
    return locations.get(SOURCE_COORDINATE).equals(SAND);
  }

  private boolean isFinish(Coordinate nextSandCoordinate) {
    return nextSandCoordinate.y() == maxY || nextSandCoordinate.x() < minX || nextSandCoordinate.x() > maxX;
  }
}
