package day22;

import static day22.Direction.RIGHT;

public class PlayerState {
  private Coordinate position;
  private Direction direction;

  public PlayerState(Coordinate position) {
    this.position = position;
    direction = RIGHT;
  }

  public Coordinate getPosition() {
    return position;
  }

  public void setPosition(Coordinate position) {
    this.position = position;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public void changeDirection(char turn) {
    direction = direction.turn(turn);
  }

  public int calculatePassword() {
    return position.y() * 1000 + position.x() * 4 + direction.getPoints();
  }
}
