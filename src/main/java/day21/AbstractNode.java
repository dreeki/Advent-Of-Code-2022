package day21;

import static java.util.Objects.isNull;

public abstract class AbstractNode {

  protected Node parent;

  private final String name;

  public AbstractNode(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public boolean isRoot() {
    return isNull(parent);
  }

  public abstract long calculateValue();
}
