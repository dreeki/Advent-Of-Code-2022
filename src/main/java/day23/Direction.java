package day23;

import static day23.DeltaCoordinate.E;
import static day23.DeltaCoordinate.N;
import static day23.DeltaCoordinate.NE;
import static day23.DeltaCoordinate.NW;
import static day23.DeltaCoordinate.S;
import static day23.DeltaCoordinate.SE;
import static day23.DeltaCoordinate.SW;
import static day23.DeltaCoordinate.W;

import java.util.List;

public enum Direction {
  NORTH(N, List.of(N, NE, NW)),
  SOUTH(S, List.of(S, SE, SW)),
  WEST(W, List.of(W, NW, SW)),
  EAST(E, List.of(E, NE, SE));

  private final DeltaCoordinate moveTo;
  private final List<DeltaCoordinate> toConsider;

  Direction(DeltaCoordinate moveTo, List<DeltaCoordinate> toConsider) {
    this.moveTo = moveTo;
    this.toConsider = toConsider;
  }

  public DeltaCoordinate getMoveTo() {
    return moveTo;
  }

  public List<DeltaCoordinate> getToConsider() {
    return toConsider;
  }
}
