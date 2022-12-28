package day24;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class Valley {
  private final Map<Integer, Map<Position, List<Direction>>> blizzardsByMinute;
  private final int minX;
  private final int maxX;
  private final int minY;
  private final int maxY;
  private final int lcm;
  private final Position startPosition;
  private final Position endPosition;

  private final List<Integer> shortestTimes = new ArrayList<>(List.of(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
  private final List<Position> startPositions;
  private final List<Position> targets;

  public Valley(List<String> lines) {
    blizzardsByMinute = new HashMap<>();

    minX = 1;
    minY = 1;
    maxX = lines.get(0).length() - 2;
    maxY = lines.size() - 2;

    startPosition = new Position(minX, minY - 1);
    endPosition = new Position(maxX, maxY + 1);
    startPositions = List.of(startPosition, endPosition, startPosition);
    targets = List.of(endPosition, startPosition, endPosition);

    lcm = calculateLcm(lines.size() - 2, lines.get(0).length() - 2);

    Map<Position, List<Direction>> initalBlizzards = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        char c = lines.get(y).charAt(x);
        if (c != '#' && c != '.') {
          initalBlizzards.put(new Position(x, y), List.of(Direction.fromChar(c)));
        }
      }
    }
    blizzardsByMinute.put(0, initalBlizzards);
//    printMinute(0);

    for (int i = 1; i < lcm; i++) {
      Map<Position, List<Direction>> previousPositions = blizzardsByMinute.get(i - 1);
      Map<Position, List<Direction>> nextPositions = new HashMap<>(previousPositions.size());

      previousPositions.forEach((position, directions) -> {
        directions.forEach(direction -> {
          Position nextPosition = position.fromDirection(direction);
          if (nextPosition.x() < minX) {
            nextPosition = new Position(maxX, nextPosition.y());
          }
          if (nextPosition.y() < minY) {
            nextPosition = new Position(nextPosition.x(), maxY);
          }
          if (nextPosition.x() > maxX) {
            nextPosition = new Position(minX, nextPosition.y());
          }
          if (nextPosition.y() > maxY) {
            nextPosition = new Position(nextPosition.x(), minY);
          }
          if (nextPositions.containsKey(nextPosition)) {
            List<Direction> list = nextPositions.get(nextPosition);
            nextPositions.put(nextPosition, Stream.concat(list.stream(), Stream.of(direction)).toList());
          } else {
            nextPositions.put(nextPosition, List.of(direction));
          }
        });
      });

      blizzardsByMinute.put(i, nextPositions);
//      printMinute(i);
    }
  }

  public void findShortestTimes() {
    for (int i = 0; i < 3; i++) {
      findShortestTimeWithRound(i);
    }
  }

  private void findShortestTimeWithRound(int round) {
    int startMinute = findStartMinute(round);
    Queue<PositionAtMinute> queue = new LinkedList<>();
    queue.add(new PositionAtMinute(startPositions.get(round), startMinute));

    while (!queue.isEmpty() && shortestTimes.get(round) == Integer.MAX_VALUE) {
      PositionAtMinute currentPositionAtMinute = queue.poll();
      currentPositionAtMinute.position.nextPositions().stream()
          .filter(this::isInField)
          .filter(position -> !blizzardsByMinute.get((currentPositionAtMinute.minute + 1) % lcm).containsKey(position))
          .map(position -> new PositionAtMinute(position, currentPositionAtMinute.minute + 1))
          .filter(positionAtMinute -> !queue.contains(positionAtMinute))
          .forEach(positionAtMinute -> {
            if (positionAtMinute.position.equals(targets.get(round))) {
              shortestTimes.set(round, positionAtMinute.minute);
            }
            queue.add(positionAtMinute);
          });
    }
  }

  private int findStartMinute(int round) {
    if(round == 0) {
      return 0;
    }
    return shortestTimes.get(round - 1);
  }

  private boolean isInField(Position position) {
    return position.equals(startPosition)
        || position.equals(endPosition)
        || withinBoundaries(position);
  }

  private boolean withinBoundaries(Position position) {
    return position.x() >= minX
        && position.x() <= maxX
        && position.y() >= minY
        && position.y() <= maxY;
  }

  private int calculateLcm(int a, int b) {
    int gcd = findGcd(a, b);
    return (b * a) / gcd;
  }

  private int findGcd(int a, int b) {
    if (b == 0) {
      return a;
    }
    return findGcd(b, a % b);
  }

  private void printMinute(int minute) {
    StringBuilder builder = new StringBuilder();
    builder.append("Minute ").append(minute).append("\n");
    builder.append("#.");
    for (int i = 2; i <= maxX + 1; i++) {
      builder.append("#");
    }
    builder.append("\n");

    for (int y = minY; y <= maxY; y++) {
      builder.append("#");
      for (int x = minX; x <= maxX; x++) {
        Position position = new Position(x, y);
        List<Direction> directions = blizzardsByMinute.get(minute).get(position);
        if (isNull(directions)) {
          builder.append(".");
        } else if (directions.size() == 1) {
          builder.append(directions.get(0));
        } else if (directions.size() > 1) {
          builder.append(directions.size());
        }
      }
      builder.append("#");
      builder.append("\n");
    }

    for (int i = 0; i <= maxX - 1; i++) {
      builder.append("#");
    }
    builder.append(".#");

    System.out.println(builder);
    System.out.println();
  }

  public List<Integer> getShortestTimes() {
    return shortestTimes;
  }

  private record PositionAtMinute(Position position, int minute) {
  }
}
