package day14;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Path (List<Coordinate> coordinates) {
  public Path(List<Coordinate> coordinates) {
    Set<Coordinate> result = new HashSet<>();

    for(int i = 0; i < coordinates.size() - 1; i++) {
      Coordinate from = coordinates.get(i);
      Coordinate to = coordinates.get(i + 1);

      int deltaX = to.x() - from.x();
      int deltaY = to.y() - from.y();

      if(deltaX > 1) {
        deltaX = 1;
      }
      if(deltaX < -1) {
        deltaX = -1;
      }
      if(deltaY > 1) {
        deltaY = 1;
      }
      if(deltaY < -1) {
        deltaY = -1;
      }

      Coordinate next = from;

      while(!next.equals(to)) {
        result.add(next);
        next = new Coordinate(next.x() + deltaX, next.y() + deltaY);
      }
      result.add(to);
    }
    this.coordinates = new ArrayList<>(result);
  }

}
