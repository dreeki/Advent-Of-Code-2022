package day12;

import static day12.Position.createBorder;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

public class Hill {
  private final int height;
  private final int width;

  private final List<List<Position>> positions;

  private final PriorityQueue<Position> positionQueue;

  public Hill(List<String> lines) {
    height = lines.size();
    width = lines.get(0).length();

    this.positions = new ArrayList<>();
    positionQueue = new PriorityQueue<>(comparingInt(Position::getShortestDistance));

    List<Position> row = new ArrayList<>();
    for (int i = 0; i < width + 2; i++) {
      row.add(createBorder(i, 0));
    }
    positions.add(row);

    for (int j = 0; j < lines.size(); j++) {
      String line = lines.get(j);
      row = new ArrayList<>();
      row.add(createBorder(0, j+1));

      for(int k = 0; k < line.length(); k++){
        row.add(new Position(k+1, j+1, line.charAt(k)));
      }

      row.add(createBorder(width+1, j+1));
      positions.add(row);
    }

    row = new ArrayList<>();
    for (int i = 0; i < width + 2; i++) {
      row.add(createBorder(i, height+1));
    }
    positions.add(row);
  }

  public void doDijkstra() {
    Position start = findStart();
    start.setShortestDistance(0);

    doDijkstraOnNeighbours(start);
  }

  public void doDijkstraReverse() {
    Position end = findEnd();
    end.setShortestDistance(0);

    doDijkstraReverseOnNeighbours(end);
  }

  public int getShortestDistanceToEnd() {
    return findEnd().getShortestDistance();
  }

  public int getShortestDistanceToStart() {
    return findStart().getShortestDistance();
  }

  public int getShortestDistanceToA() {
    return positions.stream()
        .flatMap(Collection::stream)
        .filter(Position::isA)
        .mapToInt(Position::getShortestDistance)
        .min()
        .getAsInt();
  }

  private void doDijkstraOnNeighbours(Position position) {
    checkPosition(position, find(position.x() - 1, position.y()));
    checkPosition(position, find(position.x() + 1, position.y()));
    checkPosition(position, find(position.x(), position.y() - 1));
    checkPosition(position, find(position.x(), position.y() + 1));

    Position next = positionQueue.poll();

    if(nonNull(next)){
      if(!next.isEnd()) {
        doDijkstraOnNeighbours(next);
      }
    }
  }

  private void doDijkstraReverseOnNeighbours(Position position) {
    checkPositionReverse(position, find(position.x() - 1, position.y()));
    checkPositionReverse(position, find(position.x() + 1, position.y()));
    checkPositionReverse(position, find(position.x(), position.y() - 1));
    checkPositionReverse(position, find(position.x(), position.y() + 1));

    Position next = positionQueue.poll();

    if(nonNull(next)){
      if(!next.isStart()) {
        doDijkstraReverseOnNeighbours(next);
      }
    }
  }

  private void checkPosition(Position current, Position next) {
    if(current.isPossibleNext(next) && current.getShortestDistance() < next.getShortestDistance()) {
      next.setShortestDistance(current.getShortestDistance() +1);
      if(!positionQueue.contains(next)){
        positionQueue.add(next);
      }
    }
  }

  private void checkPositionReverse(Position current, Position next) {
    if(next.isPossibleNext(current) && current.getShortestDistance() < next.getShortestDistance()) {
      next.setShortestDistance(current.getShortestDistance() +1);
      if(!positionQueue.contains(next)){
        positionQueue.add(next);
      }
    }
  }

  private Position findStart() {
    for (List<Position> row : positions) {
      for (Position position : row) {
        if(position.isStart()) {
          return position;
        }
      }
    }
    throw new IllegalStateException();
  }

  private Position findEnd() {
    for (List<Position> row : positions) {
      for (Position position : row) {
        if(position.isEnd()) {
          return position;
        }
      }
    }
    throw new IllegalStateException();
  }

  private Position find(int x, int y) {
    return positions.get(y).get(x);
  }
}
