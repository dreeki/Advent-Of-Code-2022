package day22;

import static day22.Content.OPEN_TILE;
import static day22.Content.SOLID_WALL;
import static day22.Direction.DOWN;
import static day22.Direction.LEFT;
import static day22.Direction.RIGHT;
import static day22.Direction.UP;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Jungle {
  private final PlayerState playerState;
  private final Map<Coordinate, Content> map;

  public Jungle(List<String> lines) {
    map = new HashMap<>();
    for (int i = 0; i < lines.size(); i++) {
      int y = i + 1;
      String line = lines.get(i);
      for (int j = 0; j < line.length(); j++) {
        int x = j + 1;
        if (line.charAt(j) == '.') {
          map.put(new Coordinate(x, y), OPEN_TILE);
        }
        if (line.charAt(j) == '#') {
          map.put(new Coordinate(x, y), SOLID_WALL);
        }

      }
    }
    Coordinate start = map.keySet().stream()
        .filter(coordinate -> coordinate.y() == 1)
        .sorted(Comparator.comparingInt(Coordinate::x))
        .findFirst()
        .get();
    playerState = new PlayerState(start);
  }

  public void executeCommands(List<Command> commands) {
    commands.forEach(this::executeCommand);
  }

  public void executeCommandsPart2(List<Command> commands) {
    commands.forEach(this::executeCommand2);
  }

  public int calculatePassword() {
    return playerState.calculatePassword();
  }

  private void executeCommand(Command command) {
    switch (command) {
      case MoveCommand moveCommand -> executeMoveCommand(moveCommand);
      case TurnCommand turnCommand -> executeTurnCommand(turnCommand);
    }
  }

  private void executeCommand2(Command command) {
    switch (command) {
      case MoveCommand moveCommand -> executeMoveCommand2(moveCommand);
      case TurnCommand turnCommand -> executeTurnCommand(turnCommand);
    }
  }

  private void executeTurnCommand(TurnCommand command) {
    playerState.changeDirection(command.turn());
  }

  private void executeMoveCommand(MoveCommand command) {
    Direction direction = playerState.getDirection();
    Coordinate currentPosition = playerState.getPosition();
    for (int i = 0; i < command.amount(); i++) {
      Coordinate potentialNewPosition = currentPosition.oneFurtherInDirection(direction);
      if (map.containsKey(potentialNewPosition)) {
        if (map.get(potentialNewPosition) == SOLID_WALL) {
          break;
        } else {
          currentPosition = potentialNewPosition;
        }
      } else {
        Direction oppositeDirection = direction.opposite();
        Coordinate currentFurthest = new Coordinate(currentPosition);
        Coordinate potentialNewFurthest = currentFurthest;
        while (map.containsKey(potentialNewFurthest)) {
          currentFurthest = potentialNewFurthest;
          potentialNewFurthest = potentialNewFurthest.oneFurtherInDirection(oppositeDirection);
        }
        if (map.get(currentFurthest) == SOLID_WALL) {
          break;
        } else {
          currentPosition = currentFurthest;
        }
      }
    }
    playerState.setPosition(currentPosition);
  }

  // Bah... only works for my unfolded cube layout :( all hardcoded coordinates????
  private void executeMoveCommand2(MoveCommand command) {
    Coordinate currentPosition = playerState.getPosition();
    for (int i = 0; i < command.amount(); i++) {
      Coordinate potentialNewPosition = currentPosition.oneFurtherInDirection(playerState.getDirection());
      if (map.containsKey(potentialNewPosition)) {
        if (map.get(potentialNewPosition) == SOLID_WALL) {
          break;
        } else {
          currentPosition = potentialNewPosition;
        }
      } else {
        Optional<CoordinateAndDirection> cubeSwithSideCoordinate = findNewCubeCoordinateAndDirection(potentialNewPosition);

        if (cubeSwithSideCoordinate.isEmpty()) {
          break;
        } else {
          CoordinateAndDirection coordinateAndDirection = cubeSwithSideCoordinate.get();
          currentPosition = coordinateAndDirection.coordinate;
          playerState.setDirection(coordinateAndDirection.direction);
        }
      }
    }
    playerState.setPosition(currentPosition);
  }

  private Optional<CoordinateAndDirection> findNewCubeCoordinateAndDirection(Coordinate potentialNewPositionOffGrid) {
    return switch (playerState.getDirection()) {
      case UP -> findNewCubeCoordinateAndDirectionWhenWalkingOffGridUpwards(potentialNewPositionOffGrid);
      case DOWN -> findNewCubeCoordinateAndDirectionWhenWalkingOffGridDownwards(potentialNewPositionOffGrid);
      case LEFT -> findNewCubeCoordinateAndDirectionWhenWalkingOffGridToTheLeft(potentialNewPositionOffGrid);
      case RIGHT -> findNewCubeCoordinateAndDirectionWhenWalkingOffGridToTheRight(potentialNewPositionOffGrid);
    };
  }

  private Optional<CoordinateAndDirection> findNewCubeCoordinateAndDirectionWhenWalkingOffGridUpwards(Coordinate potentialNewPositionOffGrid) {
    Coordinate newCoordinate;
    Direction newDirection;
    if (potentialNewPositionOffGrid.x() >= 1 && potentialNewPositionOffGrid.x() <= 50) {
      newCoordinate = new Coordinate(51, potentialNewPositionOffGrid.x() + 50);
      newDirection = RIGHT;
    } else if (potentialNewPositionOffGrid.x() >= 51 && potentialNewPositionOffGrid.x() <= 100) {
      newCoordinate = new Coordinate(1, potentialNewPositionOffGrid.x() + 100);
      newDirection = RIGHT;
    } else if (potentialNewPositionOffGrid.x() >= 101 && potentialNewPositionOffGrid.x() <= 150) {
      newCoordinate = new Coordinate(potentialNewPositionOffGrid.x() - 100, 200);
      newDirection = UP;
    } else {
      throw new IllegalArgumentException();
    }

    if (map.get(newCoordinate) == SOLID_WALL) {
      return Optional.empty();
    }
    return Optional.of(new CoordinateAndDirection(newCoordinate, newDirection));
  }

  private Optional<CoordinateAndDirection> findNewCubeCoordinateAndDirectionWhenWalkingOffGridDownwards(Coordinate potentialNewPositionOffGrid) {
    Coordinate newCoordinate;
    Direction newDirection;
    if (potentialNewPositionOffGrid.x() >= 1 && potentialNewPositionOffGrid.x() <= 50) {
      newCoordinate = new Coordinate(potentialNewPositionOffGrid.x() + 100, 1);
      newDirection = DOWN;
    } else if (potentialNewPositionOffGrid.x() >= 51 && potentialNewPositionOffGrid.x() <= 100) {
      newCoordinate = new Coordinate(50, potentialNewPositionOffGrid.x() + 100);
      newDirection = LEFT;
    } else if (potentialNewPositionOffGrid.x() >= 101 && potentialNewPositionOffGrid.x() <= 150) {
      newCoordinate = new Coordinate(100, potentialNewPositionOffGrid.x() - 50);
      newDirection = LEFT;
    } else {
      throw new IllegalArgumentException();
    }

    if (map.get(newCoordinate) == SOLID_WALL) {
      return Optional.empty();
    }
    return Optional.of(new CoordinateAndDirection(newCoordinate, newDirection));
  }

  private Optional<CoordinateAndDirection> findNewCubeCoordinateAndDirectionWhenWalkingOffGridToTheLeft(Coordinate potentialNewPositionOffGrid) {
    Coordinate newCoordinate;
    Direction newDirection;
    if (potentialNewPositionOffGrid.y() >= 1 && potentialNewPositionOffGrid.y() <= 50) {
      newCoordinate = new Coordinate(1, 100 + (50 - potentialNewPositionOffGrid.y() + 1));
      newDirection = RIGHT;
    } else if (potentialNewPositionOffGrid.y() >= 51 && potentialNewPositionOffGrid.y() <= 100) {
      newCoordinate = new Coordinate(potentialNewPositionOffGrid.y() - 50, 101);
      newDirection = DOWN;
    } else if (potentialNewPositionOffGrid.y() >= 101 && potentialNewPositionOffGrid.y() <= 150) {
      newCoordinate = new Coordinate(51, (150 - potentialNewPositionOffGrid.y() + 100 + 1) - 100);
      newDirection = RIGHT;
    } else if (potentialNewPositionOffGrid.y() >= 151 && potentialNewPositionOffGrid.y() <= 200) {
      newCoordinate = new Coordinate(potentialNewPositionOffGrid.y() - 100, 1);
      newDirection = DOWN;
    } else {
      throw new IllegalArgumentException();
    }

    if (map.get(newCoordinate) == SOLID_WALL) {
      return Optional.empty();
    }
    return Optional.of(new CoordinateAndDirection(newCoordinate, newDirection));
  }

  private Optional<CoordinateAndDirection> findNewCubeCoordinateAndDirectionWhenWalkingOffGridToTheRight(Coordinate potentialNewPositionOffGrid) {
    Coordinate newCoordinate;
    Direction newDirection;
    if (potentialNewPositionOffGrid.y() >= 1 && potentialNewPositionOffGrid.y() <= 50) {
      newCoordinate = new Coordinate(100, 150 - potentialNewPositionOffGrid.y() + 1);
      newDirection = LEFT;
    } else if (potentialNewPositionOffGrid.y() >= 51 && potentialNewPositionOffGrid.y() <= 100) {
      newCoordinate = new Coordinate(potentialNewPositionOffGrid.y() + 50, 50);
      newDirection = UP;
    } else if (potentialNewPositionOffGrid.y() >= 101 && potentialNewPositionOffGrid.y() <= 150) {
      newCoordinate = new Coordinate(150, (150 - potentialNewPositionOffGrid.y() + 100 + 1) - 100);
      newDirection = LEFT;
    } else if (potentialNewPositionOffGrid.y() >= 151 && potentialNewPositionOffGrid.y() <= 200) {
      newCoordinate = new Coordinate(potentialNewPositionOffGrid.y() - 100, 150);
      newDirection = UP;
    } else {
      throw new IllegalArgumentException();
    }

    if (map.get(newCoordinate) == SOLID_WALL) {
      return Optional.empty();
    }
    return Optional.of(new CoordinateAndDirection(newCoordinate, newDirection));
  }

  private record CoordinateAndDirection(Coordinate coordinate, Direction direction) {
  }
}
