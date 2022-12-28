package day21;

public class Leaf extends AbstractNode {
  private final long value;

  public Leaf(String name, long value) {
    super(name);
    this.value = value;
  }

  @Override
  public long calculateValue() {
    return value;
  }

  public long getValueForEquality() {
    return parent.getValueForEquality(this);
  }
}
