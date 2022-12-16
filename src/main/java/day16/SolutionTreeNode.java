package day16;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SolutionTreeNode {
  private final String name;
  private final int flowRate;
  private final int timeOpened;
  private SolutionTreeNode parent;
  private List<SolutionTreeNode> children;

  public SolutionTreeNode(String name, int flowRate, int timeOpened) {
    this.name = name;
    this.flowRate = flowRate;
    this.timeOpened = timeOpened;

    children = new ArrayList<>();
  }

  public String name() {
    return name;
  }

  public int time() {
    return timeOpened;
  }

  public void addChild(SolutionTreeNode node) {
    children.add(node);
    node.parent = this;
  }

  public List<String> visitedNodes() {
    List<String> result = new ArrayList<>();

    SolutionTreeNode current = this;
    while(nonNull(current)) {
      result.add(current.name);
      current = current.parent;
    }

    return result;
  }

  public List<SolutionTreeNode> findLeaves() {
    if(isLeaf()) {
      return List.of(this);
    }
    return children.stream().map(SolutionTreeNode::findLeaves).flatMap(Collection::stream).toList();
  }

  private boolean isLeaf() {
    return children.isEmpty();
  }

  public SolutionTreeNode getParent() {
    return parent;
  }

  public int calculateScore(int totalTime) {
    return (totalTime - timeOpened) * flowRate;
  }
}
