package day17;

import java.util.List;

// Main position is most left and then most bottom
public enum Rock {
  HORIZONTAL_LINE(List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0))),
  PLUS(List.of(new Position(0, 0), new Position(1, 0), new Position(1, 1), new Position(1, -1), new Position(2, 0))),
  CORNER(List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(2, 1), new Position(2, 2))),
  VERTICAL_LINE(List.of(new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(0, 3))),
  BLOCK(List.of(new Position(0, 0), new Position(1, 0), new Position(0, 1), new Position(1, 1)));

  private final List<Position> relativePositions;

  Rock(List<Position> relativePositions) {
    this.relativePositions = relativePositions;
  }

  public List<Position> getAbsolutePositionsForMainPosition(Position mainPosition) {
    return relativePositions.stream()
        .map(position -> new Position(mainPosition.x() + position.x(), mainPosition.y() + position.y()))
        .toList();
  }

  public int getAdditionalHeightFromMainToBottom() {
    if(this == PLUS) {
      return 1;
    }
    return 0;
  }
}
