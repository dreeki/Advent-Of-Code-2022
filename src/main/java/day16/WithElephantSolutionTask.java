package day16;

import static day16.Cave.TOTAL_TIME_WITH_ELEPHANT;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class WithElephantSolutionTask implements Callable<Integer> {

  public static final int BATCH_SIZE = 100;
  private final int batchNumber;
  private final Map<SolutionTreeNode, Collection<SolutionTreeNode>> ancestryPerLeaf;

  public WithElephantSolutionTask(int batchNumber, Map<SolutionTreeNode, Collection<SolutionTreeNode>> ancestryPerLeaf) {
    this.batchNumber = batchNumber;
    this.ancestryPerLeaf = ancestryPerLeaf;
  }

  @Override
  public Integer call() {
//    System.out.printf("Batch %d: Started%n", batchNumber);
    List<SolutionTreeNode> leaves = new ArrayList<>(ancestryPerLeaf.keySet());

    int maxResult = 0;
    for(int i = batchNumber * BATCH_SIZE; i < (batchNumber+1) * BATCH_SIZE && i < leaves.size(); i++) {
      for(int j = i + 1; j < leaves.size(); j++) {
        int score = findScoreBetweenPaths(ancestryPerLeaf.get(leaves.get(i)), ancestryPerLeaf.get(leaves.get(j)));
        maxResult = Math.max(maxResult, score);
      }
    }
//    System.out.printf("Batch %d: Done%n", batchNumber);
    return maxResult;
  }

  private int findScoreBetweenPaths(Collection<SolutionTreeNode> myPath, Collection<SolutionTreeNode> elephantPath) {
    return Stream.concat(myPath.stream(), elephantPath.stream())
        .collect(groupingBy(SolutionTreeNode::name, mapping(node -> node.calculateScore(TOTAL_TIME_WITH_ELEPHANT), toSet())))
        .values()
        .stream()
        .mapToInt(set -> set.stream().mapToInt(i -> i).max().getAsInt())
        .sum();
  }
}
