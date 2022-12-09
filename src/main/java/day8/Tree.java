package day8;

class Tree {
  private final int height;

  public Tree(int height) {
    this.height = height;
  }

  boolean isTallerThan(Tree tree) {
    return height > tree.height;
  }

  @Override
  public String toString() {
    return "Tree{" +
        "height=" + height +
        '}';
  }
}
