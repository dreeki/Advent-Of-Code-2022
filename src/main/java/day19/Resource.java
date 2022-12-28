package day19;

public enum Resource {
  ORE(0),
  CLAY(1),
  OBSIDIAN(2),
  GEODE(3);

  private final int index;

  public int getIndex() {
    return index;
  }

  Resource(int index) {
    this.index = index;
  }
}
