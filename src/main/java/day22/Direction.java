package day22;

public enum Direction {
  UP(0, -1, 3) {
    @Override
    Direction turnRight() {
      return RIGHT;
    }

    @Override
    Direction turnLeft() {
      return LEFT;
    }

    @Override
    Direction opposite() {
      return DOWN;
    }
  },
  RIGHT(1, 0, 0) {
    @Override
    Direction turnRight() {
      return DOWN;
    }

    @Override
    Direction turnLeft() {
      return UP;
    }

    @Override
    Direction opposite() {
      return LEFT;
    }
  },
  DOWN(0, 1, 1) {
    @Override
    Direction turnRight() {
      return LEFT;
    }

    @Override
    Direction turnLeft() {
      return RIGHT;
    }

    @Override
    Direction opposite() {
      return UP;
    }
  },
  LEFT(-1, 0, 2) {
    @Override
    Direction turnRight() {
      return UP;
    }

    @Override
    Direction turnLeft() {
      return DOWN;
    }

    @Override
    Direction opposite() {
      return RIGHT;
    }
  };

  private final int deltaX;
  private final int deltaY;
  private final int points;

  Direction(int deltaX, int deltaY, int points) {
    this.deltaX = deltaX;
    this.deltaY = deltaY;
    this.points = points;
  }

  public int getDeltaX() {
    return deltaX;
  }

  public int getDeltaY() {
    return deltaY;
  }

  public int getPoints() {
    return points;
  }

  abstract Direction turnRight();

  abstract Direction turnLeft();

  abstract Direction opposite();

  public Direction turn(char direction) {
    return switch(direction) {
      case 'R' -> this.turnRight();
      case 'L' -> this.turnLeft();
      default -> throw new UnsupportedOperationException();
    };
  }
}
