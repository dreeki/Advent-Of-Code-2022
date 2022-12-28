package day17;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Tower {

  private static final int PART_ONE = 2022;
  private final Queue<Push> pushes;
  private final Queue<Rock> rocks;
  private final List<Position> blocksInTower;
  private final List<Long> heightIncreases;
  private final List<PushRock> cycleDetectionList;

  private static final int MIN_X = 1;
  private static final int MAX_X = 7;

  public Tower(String line) {
    pushes = new LinkedList<>();
    for (int i = 0; i < line.length(); i++) {
      pushes.add(new Push(i, Direction.fromChar(line.charAt(i))));
    }
    rocks = new LinkedList<>(Arrays.stream(Rock.values()).toList());

    blocksInTower = new ArrayList<>(IntStream.rangeClosed(MIN_X, MAX_X).mapToObj(x -> new Position(x, 0)).toList());

    heightIncreases = new ArrayList<>(PART_ONE);
    cycleDetectionList = new ArrayList<>(PART_ONE);
  }

  public long dropRocks(long amount) {
    long currentHeight = 0;
    if (cycleDetectionList.isEmpty()) {
      for (long i = 0; i < 3000; i++) {
        currentHeight = dropRock(currentHeight);
      }
    }
    Pattern pattern = findPattern();

    long result = 0;
    long amountTodo = amount;
    for (int i = 0; i < pattern.start; i++) {
      result += heightIncreases.get(i);
      amountTodo--;
    }
    long patternOccurrence = amountTodo / pattern.length;
    result += patternOccurrence * pattern.heightIncrease;

    long rest = amountTodo % pattern.length;
    for (int i = 0; i < rest; i++) {
      result += heightIncreases.get(pattern.start + i);
    }

    return result;
  }

  private Pattern findPattern() {
    for (int start = 20; start < cycleDetectionList.size(); start++) {
      PushRock pushRockAtStart = cycleDetectionList.get(start);
      for (int i = start + 1; i < cycleDetectionList.size(); i++) {
        if (cycleDetectionList.get(i).equals(pushRockAtStart)) {
          int patternLength = i - start;
          long increase = 0;
          for (int j = start; j < start + patternLength; j++) {
            increase += heightIncreases.get(j);
          }
          return new Pattern(start, patternLength, increase);
        }
      }
    }
    throw new IllegalStateException();
  }

  private long dropRock(long currentTowerHeight) {
    Rock rock = rocks.poll();

    Position dropPosition = new Position(3, currentTowerHeight + 4 + (long) rock.getAdditionalHeightFromMainToBottom());
    boolean canDrop = true;
    long newBlockHeight = 0;
    boolean initialPush = true;
    while (canDrop) {
      Push push = pushes.poll();
      if (initialPush) {
        initialPush = false;
        Map<Long, Long> maxYPerX = blocksInTower.stream()
            .collect(toMap(Position::x, Position::y, BinaryOperator.maxBy(Comparator.comparingLong(i -> i))));
        long[] skyline = new long[7];
        for (long i = 0; i < 7; i++) {
          skyline[(int) i] = currentTowerHeight - maxYPerX.get(i + 1);
        }
        cycleDetectionList.add(new PushRock(push, rock, skyline));
      }

      List<Position> absolutePositions = rock.getAbsolutePositionsForMainPosition(dropPosition);
      List<Position> afterPush = absolutePositions.stream()
          .map(position -> position.fromDirection(push.direction()))
          .filter(position -> position.x() >= MIN_X)
          .filter(position -> position.x() <= MAX_X)
          .filter(position -> !blocksInTower.contains(position))
          .toList();

      List<Position> positionsToDrop = absolutePositions.size() == afterPush.size() ? afterPush : absolutePositions;
      List<Position> dropped = positionsToDrop.stream()
          .map(Position::drop)
          .filter(position -> !blocksInTower.contains(position))
          .toList();

      if (dropped.size() != absolutePositions.size()) {
        canDrop = false;
        blocksInTower.addAll(positionsToDrop);
        newBlockHeight = positionsToDrop.stream().mapToLong(Position::y).max().getAsLong();
      } else {

        dropPosition = dropped.get(0);
      }

      pushes.add(push);
    }

    rocks.add(rock);

    if (newBlockHeight > currentTowerHeight) {
      heightIncreases.add(newBlockHeight - currentTowerHeight);
    } else {
      heightIncreases.add(0L);
    }
    return Math.max(currentTowerHeight, newBlockHeight);
  }

  private record Pattern(int start, int length, long heightIncrease) {
  }

  private record PushRock(Push push, Rock rock, long[] skyline) {
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      PushRock pushRock = (PushRock) o;
      return Objects.equals(push, pushRock.push) && rock == pushRock.rock && Arrays.equals(skyline, pushRock.skyline);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(push, rock);
      result = 31 * result + Arrays.hashCode(skyline);
      return result;
    }
  }
}
