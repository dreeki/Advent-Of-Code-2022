package day23;

import static java.util.Objects.isNull;

import java.util.Optional;

public class Elf {
  private Coordinate position;
  private Coordinate nextPosition;

  public Elf(Coordinate position) {
    this.position = position;
    nextPosition = null;
  }

  public Coordinate getPosition() {
    return position;
  }

  public Optional<Coordinate> getNextPosition() {
    return Optional.ofNullable(nextPosition);
  }

  public void setNextPosition(Coordinate nextPosition) {
    this.nextPosition = nextPosition;
  }

  public void resetNextPosition() {
    nextPosition = null;
  }

  public void moveToNextPosition() {
    if(isNull(nextPosition)) {
      throw new IllegalStateException();
    }
    position = nextPosition;
  }
}
