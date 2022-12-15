package day15;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cave {
  private final Map<Coordinate, Integer> sensorsWithDistanceToClosestBeacon;
  private final Set<Coordinate> occupiedLocations;

  public Cave(List<String> lines) {
    Pattern pattern = Pattern.compile("-?\\d+");

    sensorsWithDistanceToClosestBeacon = new HashMap<>();
    occupiedLocations = new HashSet<>();
    lines.forEach(line -> {
      Matcher matcher = pattern.matcher(line);
      matcher.find();
      int sensorX = Integer.parseInt(matcher.group());
      matcher.find();
      int sensorY = Integer.parseInt(matcher.group());
      matcher.find();
      int beaconX = Integer.parseInt(matcher.group());
      matcher.find();
      int beaconY = Integer.parseInt(matcher.group());
      System.out.println();

      Coordinate sensorCoordinate = new Coordinate(sensorX, sensorY);
      Coordinate beaconCoordinate = new Coordinate(beaconX, beaconY);
      int manhattanDistance = sensorCoordinate.distance(beaconCoordinate);

      occupiedLocations.add(sensorCoordinate);
      occupiedLocations.add(beaconCoordinate);
      sensorsWithDistanceToClosestBeacon.put(sensorCoordinate, manhattanDistance);
    });
  }

  public long countEmptySpotsAtRow(int row) {
    return sensorsWithDistanceToClosestBeacon.entrySet().stream()
        .map(e -> e.getKey().findXCoordinatesForRowAndDistance(row, e.getValue()))
        .flatMap(Collection::stream)
        .distinct()
        .count() - occupiedLocations.stream().filter(coordinate -> coordinate.y() == row).map(Coordinate::x).count();
  }

  public Coordinate findDistressBeacon() {
    for (Entry<Coordinate, Integer> sensorWithDistanceToClosestBeacon : sensorsWithDistanceToClosestBeacon.entrySet()) {
      Coordinate sensorLocation = sensorWithDistanceToClosestBeacon.getKey();
      int distanceToClosestBeacon = sensorWithDistanceToClosestBeacon.getValue();
      Set<Coordinate> candidates = new HashSet<>();
      int candidateDistance = distanceToClosestBeacon + 1;
      for (int xDiff = 0; xDiff <= candidateDistance; xDiff++) {
        int yDiff = candidateDistance - xDiff;
        addCandidate(candidates, new Coordinate(sensorLocation.x() + xDiff, sensorLocation.y() + yDiff));
        addCandidate(candidates, new Coordinate(sensorLocation.x() + xDiff, sensorLocation.y() - yDiff));
        addCandidate(candidates, new Coordinate(sensorLocation.x() - xDiff, sensorLocation.y() + yDiff));
        addCandidate(candidates, new Coordinate(sensorLocation.x() - xDiff, sensorLocation.y() - yDiff));
      }
      Optional<Coordinate> result = candidates.stream().filter(this::isGoodCandidate).findFirst();
      if (result.isPresent()) {
        return result.get();
      }
    }
    throw new IllegalStateException();
  }

  private static void addCandidate(Set<Coordinate> candidates, Coordinate candidate) {
    if (candidate.x() >= 0 && candidate.y() >= 0 && candidate.x() <= 4000000 && candidate.y() <= 4000000) {
      candidates.add(candidate);
    }
  }

  private boolean isGoodCandidate(Coordinate candidate) {
    return sensorsWithDistanceToClosestBeacon.entrySet().stream().allMatch(entry -> entry.getKey().distance(candidate) > entry.getValue());
  }
}
