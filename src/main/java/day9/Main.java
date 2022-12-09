package day9;

import static day9.Direction.D;
import static day9.Direction.L;
import static day9.Direction.R;
import static day9.Direction.U;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Main {
  private static Position headPosition = new Position(0, 0);
  private static Position tailPosition = new Position(0, 0);

  private static List<Position> tailPositions;

  public static void main(String[] args) throws IOException {
    List<Direction> directions = readInputPuzzle1();
    tailPositions = new ArrayList<>(IntStream.generate(() ->  1)
        .limit(9)
        .mapToObj(i -> new Position(0, 0))
        .toList());
    Set<Position> smallRopeVisitedTailPositions = new HashSet<>();
    Set<Position> longRopeVisitedTailPositions = new HashSet<>();

    directions.forEach(direction -> {
      headPosition = headPosition.move(direction);
      List<Direction> smallRopeTailDirectionsToCatchUpWithHead = directionsToCatchUpWith(tailPosition, headPosition);

      smallRopeTailDirectionsToCatchUpWithHead.forEach(tailDirection -> tailPosition = tailPosition.move(tailDirection));
      smallRopeVisitedTailPositions.add(tailPosition);

      List<Direction> longRopeTailDirectionsToCatchUpWithHead = directionsToCatchUpWith(tailPositions.get(0), headPosition);
      longRopeTailDirectionsToCatchUpWithHead.forEach(tailDirection -> tailPositions.set(0, tailPositions.get(0).move(tailDirection)));

      for(int i = 1; i < tailPositions.size(); i++) {
        longRopeTailDirectionsToCatchUpWithHead = directionsToCatchUpWith(tailPositions.get(i), tailPositions.get(i-1));
        for (Direction tailDirection : longRopeTailDirectionsToCatchUpWithHead) {
          tailPositions.set(i, tailPositions.get(i).move(tailDirection));
        }
      }
      longRopeVisitedTailPositions.add(tailPositions.get(tailPositions.size()-1));
    });

    System.out.printf("Puzzle1: %d%n", smallRopeVisitedTailPositions.size());
    System.out.printf("Puzzle2: %d%n", longRopeVisitedTailPositions.size());
  }

  private static List<Direction> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day9", "puzzle1.txt");
    return Files.lines(path)
        .flatMap(Main::parseLine)
        .toList();
  }

  private static Stream<Direction> parseLine(String line) {
    String[] splitted = line.split(" ");
    Direction direction = Direction.valueOf(splitted[0]);
    int amount = Integer.parseInt(splitted[1]);

    return IntStream.generate(() -> 1)
        .limit(amount)
        .mapToObj(i -> direction);
  }

  private static List<Direction> directionsToCatchUpWith(Position tailPosition, Position headPosition) {
    if(tailPosition.isAdjacent(headPosition)) {
      return emptyList();
    }

    List<Direction> directions = new ArrayList<>();
    int horizontalDistance = headPosition.x() - tailPosition.x();
    int verticalDistance = headPosition.y() - tailPosition.y();


    if(horizontalDistance >= 1) {
      directions.add(R);
    }
    if(horizontalDistance <= -1) {
      directions.add(L);
    }

    if(verticalDistance >= 1) {
      directions.add(U);
    }
    if(verticalDistance <= -1) {
      directions.add(D);
    }

    return directions;
  }
}
