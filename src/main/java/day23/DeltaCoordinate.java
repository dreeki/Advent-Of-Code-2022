package day23;

import java.util.Arrays;
import java.util.List;

public enum DeltaCoordinate {
  N(0, -1),
  NE(1, -1),
  E(1, 0),
  SE(1, 1),
  S(0, 1),
  SW(-1, 1),
  W(-1, 0),
  NW(-1, -1);

  private final int deltaX;
  private final int deltaY;

  DeltaCoordinate(int deltaX, int deltaY) {
    this.deltaX = deltaX;
    this.deltaY = deltaY;
  }

  public int getDeltaX() {
    return deltaX;
  }

  public int getDeltaY() {
    return deltaY;
  }

  public static List<DeltaCoordinate> ALL = Arrays.stream(DeltaCoordinate.values()).toList();
}
