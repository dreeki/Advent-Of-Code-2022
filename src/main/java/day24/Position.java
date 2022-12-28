package day24;

import static day24.Direction.DOWN;
import static day24.Direction.LEFT;
import static day24.Direction.RIGHT;
import static day24.Direction.UP;

import java.util.List;

public record Position(int x, int y) {

  public Position fromDirection(Direction direction) {
    return new Position(x + direction.getDeltaX(), y + direction.getDeltaY());
  }

  public List<Position> nextPositions() {
    return List.of(this, fromDirection(UP), fromDirection(DOWN), fromDirection(LEFT), fromDirection(RIGHT));
  }
}
