package day23;

import static day23.Direction.EAST;
import static day23.Direction.NORTH;
import static day23.Direction.SOUTH;
import static day23.Direction.WEST;
import static java.util.stream.Collectors.groupingBy;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

public class Crater {
  private final Queue<Direction> directionOrder;
  private final Map<Coordinate, Elf> elves;

  public Crater(List<String> lines) {
    directionOrder = new LinkedList<>(List.of(NORTH, SOUTH, WEST, EAST));
    elves = new HashMap<>();

    for(int y = 0; y < lines.size(); y++) {
      for(int x = 0; x < lines.get(y).length(); x++) {
        if(lines.get(y).charAt(x) == '#') {
          Coordinate position = new Coordinate(x, y);
          elves.put(position, new Elf(position));
        }
      }
    }
  }

  public void doRounds(int amount) {
    for(int i = 0; i < amount; i++) {
      doRound();
    }
  }

  public int countEmptyTilesOfRectangle() {
    List<Integer> xValues = elves.keySet().stream().map(Coordinate::x).sorted().toList();
    List<Integer> yValues = elves.keySet().stream().map(Coordinate::y).sorted().toList();
    int minX = xValues.get(0);
    int maxX = xValues.get(xValues.size()-1);
    int minY = yValues.get(0);
    int maxY = yValues.get(yValues.size()-1);

    int width = maxX - minX + 1;
    int height = maxY - minY + 1;
    int area = width * height;

    return area - elves.values().size();
  }

  public int keepDoingRounds() {
    int result = 0;
    boolean done = false;
    while(!done) {
      done = doRound();
      result++;
    }
    return result;
  }

  private boolean doRound() {
    elves.values().forEach(this::findNextPosition);

    List<Elf> elvesToMove = elves.values().stream()
        .filter(elf -> elf.getNextPosition().isPresent())
        .collect(groupingBy(Elf::getNextPosition))
        .values()
        .stream()
        .filter(list -> list.size() == 1)
        .flatMap(Collection::stream)
        .toList();
    elvesToMove
        .forEach(elf -> {
          elves.remove(elf.getPosition());
          elf.moveToNextPosition();
          elves.put(elf.getPosition(), elf);
        });
    elves.values().forEach(Elf::resetNextPosition);

    directionOrder.add(directionOrder.poll());
    return elvesToMove.isEmpty();
  }

  private void findNextPosition(Elf elf) {
    List<Coordinate> coordinatesWithElves = DeltaCoordinate.ALL.stream()
        .map(deltaCoordinate -> elf.getPosition().fromDeltaCoordinate(deltaCoordinate))
        .filter(elves::containsKey)
        .toList();

    if(coordinatesWithElves.isEmpty()) {
      return;
    }
    Optional<Direction> chosenDirection = directionOrder.stream()
        .filter(direction -> doesNotContainElvesOnToConsiderCoordinates(elf, coordinatesWithElves, direction))
        .findFirst();

    chosenDirection.ifPresent(direction -> elf.setNextPosition(elf.getPosition().fromDeltaCoordinate(direction.getMoveTo())));
  }

  private static boolean doesNotContainElvesOnToConsiderCoordinates(Elf elf, List<Coordinate> coordinatesWithElves, Direction direction) {
    return direction.getToConsider().stream()
        .noneMatch(deltaCoordinate -> coordinatesWithElves.contains(elf.getPosition().fromDeltaCoordinate(deltaCoordinate)));
  }

  @Override
  public String toString() {
    List<Integer> xValues = elves.keySet().stream().map(Coordinate::x).sorted().toList();
    List<Integer> yValues = elves.keySet().stream().map(Coordinate::y).sorted().toList();
    int minX = xValues.get(0);
    int maxX = xValues.get(xValues.size()-1);
    int minY = yValues.get(0);
    int maxY = yValues.get(yValues.size()-1);

    StringBuilder builder = new StringBuilder();
    for(int y = minY; y <= maxY; y++) {
      for(int x = minX; x <= maxX; x++) {
        if(elves.containsKey(new Coordinate(x, y))) {
          builder.append('#');
        }else {
          builder.append('.');
        }
      }
      builder.append("\n");
    }

    return builder.toString();
  }
}
