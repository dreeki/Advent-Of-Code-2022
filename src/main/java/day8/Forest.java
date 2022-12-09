package day8;

import java.util.ArrayList;
import java.util.List;

class Forest {
  private final int height;
  private final int width;
  private int heightestScenicScore;

  private final List<List<Tree>> trees;

  public Forest(List<String> lines) {
    height = lines.size();
    width = lines.get(0).length();
    heightestScenicScore = 0;

    this.trees = new ArrayList<>();

    List<Tree> row = new ArrayList<>();
    for (int i = 0; i < width + 2; i++) {
      row.add(dummyEdgeTree());
    }
    trees.add(row);

    for (String line : lines) {
      row = new ArrayList<>();
      row.add(dummyEdgeTree());

      line.chars().mapToObj(i -> (char) i + "").map(Integer::parseInt).map(Tree::new).forEach(row::add);

      row.add(dummyEdgeTree());
      trees.add(row);
    }

    row = new ArrayList<>();
    for (int i = 0; i < width + 2; i++) {
      row.add(dummyEdgeTree());
    }
    trees.add(row);
  }

  public int findAmountOfVisibleTrees() {
    int result = 0;
    for(int x = 1; x <= width; x++) {
      for(int y = 1; y <= height; y++) {
        if(isVisiblePosition(x, y)) {
          result += 1;
        }
        heightestScenicScore = Math.max(heightestScenicScore, calculateScenicScore(x, y));
      }
    }
    return result;
  }

  private int calculateScenicScore(int x, int y) {
    return amountOfVisibleTreesLeft(x, y) * amountOfVisibleTreesRight(x, y) * amountOfVisibleTreesUp(x, y) * amountOfVisibleTreesDown(x, y);
  }

  private int amountOfVisibleTreesLeft(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    int visibleTreeCount = 0;
    for (int i = x-1; i >= 1; i--) {
      if (toCheck.isTallerThan(trees.get(y).get(i))) {
        visibleTreeCount++;
      } else {
        visibleTreeCount++;
        break;
      }
    }
    return visibleTreeCount;
  }

  private int amountOfVisibleTreesRight(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    int visibleTreeCount = 0;
    for (int i = x+1; i <= width; i++) {
      if (toCheck.isTallerThan(trees.get(y).get(i))) {
        visibleTreeCount++;
      } else {
        visibleTreeCount++;
        break;
      }
    }
    return visibleTreeCount;
  }

  private int amountOfVisibleTreesUp(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    int visibleTreeCount = 0;
    for (int i = y-1; i >= 1; i--) {
      if (toCheck.isTallerThan(trees.get(i).get(x))) {
        visibleTreeCount++;
      } else {
        visibleTreeCount++;
        break;
      }
    }
    return visibleTreeCount;
  }

  private int amountOfVisibleTreesDown(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    int visibleTreeCount = 0;
    for (int i = y+1; i <= width; i++) {
      if (toCheck.isTallerThan(trees.get(i).get(x))) {
        visibleTreeCount++;
      } else {
        visibleTreeCount++;
        break;
      }
    }
    return visibleTreeCount;
  }

  private boolean isVisiblePosition(int x, int y) {
    return isVisibleLeft(x, y) ||
        isVisibleRight(x, y) ||
        isVisibleUp(x, y) ||
        isVisibleDown(x, y);
  }

  private boolean isVisibleLeft(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    for (int i = 0; i < x; i++) {
      if (!toCheck.isTallerThan(trees.get(y).get(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean isVisibleRight(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    for (int i = x + 1; i < trees.get(y).size(); i++) {
      if (!toCheck.isTallerThan(trees.get(y).get(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean isVisibleUp(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    for (int i = 0; i < y; i++) {
      if (!toCheck.isTallerThan(trees.get(i).get(x))) {
        return false;
      }
    }
    return true;
  }

  private boolean isVisibleDown(int x, int y) {
    Tree toCheck = trees.get(y).get(x);
    for (int i = y + 1; i < trees.size(); i++) {
      if (!toCheck.isTallerThan(trees.get(i).get(x))) {
        return false;
      }
    }
    return true;
  }

  private Tree dummyEdgeTree() {
    return new Tree(-1);
  }

  public int getHeightestScenicScore() {
    return heightestScenicScore;
  }
}
