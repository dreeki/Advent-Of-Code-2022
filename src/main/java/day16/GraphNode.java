package day16;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

public class GraphNode {
  private final String name;
  private final int flowRate;
  private List<GraphNode> neighbours;

  public GraphNode(String name, int flowRate) {
    this.name = name;
    this.flowRate = flowRate;

    neighbours = new ArrayList<>();
  }

  public int getFlowRate() {
    return flowRate;
  }

  public String getName() {
    return name;
  }

  public void addNeighbour(GraphNode graphNode) {
    neighbours.add(graphNode);
  }

  public List<GraphNode> getNeighbours() {
    return neighbours;
  }
}
