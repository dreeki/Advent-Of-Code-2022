package day16;

import static day16.WithElephantSolutionTask.BATCH_SIZE;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toConcurrentMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Cave {
  private static final int TOTAL_TIME_ALONE = 30;
  public static final int TOTAL_TIME_WITH_ELEPHANT = 26;

  private Map<String, GraphNode> nodesByName;
  private Map<String, Map<String, Integer>> distancesFromTo;
  private SolutionTreeNode solutionTreeRootAlone;

  private SolutionTreeNode solutionTreeRootWithElephant;

  public Cave(List<String> lines) {
    nodesByName = new HashMap<>();
    distancesFromTo = new HashMap<>();
    solutionTreeRootAlone = new SolutionTreeNode("start", 0, 0);
    solutionTreeRootWithElephant = new SolutionTreeNode("start", 0, 0);

    Pattern namePattern = Pattern.compile("Valve [A-Z]{2}");
    Pattern flowRatePattern = Pattern.compile("\\d+");

    lines.forEach(line -> {
      Matcher nameMatcher = namePattern.matcher(line);
      nameMatcher.find();
      String name = nameMatcher.group().split(" ")[1];

      Matcher flowRateMatcher = flowRatePattern.matcher(line);
      flowRateMatcher.find();
      int flowRate = Integer.parseInt(flowRateMatcher.group());

      GraphNode graphNode = new GraphNode(name, flowRate);
      nodesByName.put(name, graphNode);
    });

    Pattern neighboursPattern1 = Pattern.compile("valve[s]? [A-Z]{2}(, [A-Z]{2})*");
    Pattern neighboursPattern2 = Pattern.compile("[A-Z]{2}");
    lines.forEach(line -> {
      Matcher nameMatcher = namePattern.matcher(line);
      nameMatcher.find();
      String name = nameMatcher.group().split(" ")[1];
      GraphNode current = nodesByName.get(name);

      Matcher neighboursMatcher1 = neighboursPattern1.matcher(line);
      neighboursMatcher1.find();
      String temp = neighboursMatcher1.group();

      Matcher neighboursMatcher2 = neighboursPattern2.matcher(temp);
      while (neighboursMatcher2.find()) {
        String neighbour = neighboursMatcher2.group();
        current.addNeighbour(nodesByName.get(neighbour));
      }
    });

    calculateDistances();
  }

  public void openValvesSolo() {
    String startName = "AA";
    Queue<SolutionTreeNode> queue = new LinkedList<>();

    List<PointsPotentialHelper> initialPositions = distancesFromTo.get(startName).entrySet().stream()
        .filter(e -> nodesByName.get(e.getKey()).getFlowRate() != 0)
        .map(entry -> new PointsPotentialHelper(entry.getKey(), entry.getValue() + 1))
        .toList();

    initialPositions.forEach(next -> {
      SolutionTreeNode node = new SolutionTreeNode(next.node, nodesByName.get(next.node).getFlowRate(), next.timeToOpenNode);
      solutionTreeRootAlone.addChild(node);
      queue.add(node);
    });

    while (!queue.isEmpty()) {
      SolutionTreeNode current = queue.poll();
      List<String> visitedNodes = current.visitedNodes();

      List<PointsPotentialHelper> potentialNexts = distancesFromTo.get(current.name()).entrySet().stream()
          .filter(e -> nodesByName.get(e.getKey()).getFlowRate() != 0)
          .filter(e -> !visitedNodes.contains(e.getKey()))
          .filter(e -> current.time() + e.getValue() + 1 < TOTAL_TIME_ALONE)
          .map(entry -> new PointsPotentialHelper(entry.getKey(), current.time() + entry.getValue() + 1))
          .toList();

      potentialNexts.forEach(next -> {
        SolutionTreeNode node = new SolutionTreeNode(next.node, nodesByName.get(next.node).getFlowRate(), next.timeToOpenNode);
        current.addChild(node);
        queue.add(node);
      });
    }
  }

  public void openValvesDuo() {
    String startName = "AA";
    Queue<SolutionTreeNode> queue = new LinkedList<>();

    List<PointsPotentialHelper> initialPositions = distancesFromTo.get(startName).entrySet().stream()
        .filter(e -> nodesByName.get(e.getKey()).getFlowRate() != 0)
        .map(entry -> new PointsPotentialHelper(entry.getKey(), entry.getValue() + 1))
        .toList();

    initialPositions.forEach(next -> {
      SolutionTreeNode node = new SolutionTreeNode(next.node, nodesByName.get(next.node).getFlowRate(), next.timeToOpenNode);
      solutionTreeRootWithElephant.addChild(node);
      queue.add(node);
    });

    while (!queue.isEmpty()) {
      SolutionTreeNode current = queue.poll();
      List<String> visitedNodes = current.visitedNodes();

      List<PointsPotentialHelper> potentialNexts = distancesFromTo.get(current.name()).entrySet().stream()
          .filter(e -> nodesByName.get(e.getKey()).getFlowRate() != 0)
          .filter(e -> !visitedNodes.contains(e.getKey()))
          .filter(e -> current.time() + e.getValue() + 1 < TOTAL_TIME_WITH_ELEPHANT)
          .map(entry -> new PointsPotentialHelper(entry.getKey(), current.time() + entry.getValue() + 1))
          .toList();

      potentialNexts.forEach(next -> {
        SolutionTreeNode node = new SolutionTreeNode(next.node, nodesByName.get(next.node).getFlowRate(), next.timeToOpenNode);
        current.addChild(node);
        queue.add(node);
      });
    }
  }

  public int calculateMaximumPressureReleaseAlone() {
    return solutionTreeRootAlone.findLeaves().stream().mapToInt(leave -> {
      SolutionTreeNode current = leave;
      int score = 0;

      while (nonNull(current)) {
        score += current.calculateScore(TOTAL_TIME_ALONE);
        current = current.getParent();
      }

      return score;
    }).max().getAsInt();
  }

  public int calculateMaximumPressureReleaseWithElephant() {
    Map<SolutionTreeNode, Collection<SolutionTreeNode>> ancestryPerLeaf = solutionTreeRootWithElephant.findLeaves().stream()
        .collect(toConcurrentMap(leaf -> leaf, leaf -> {
          Collection<SolutionTreeNode> ancestry = new ConcurrentLinkedQueue<>();
          SolutionTreeNode current = leaf;
          while (nonNull(current)) {
            ancestry.add(current);
            current = current.getParent();
          }
          return ancestry;
        }));

    int maxResult = 0;
    List<SolutionTreeNode> leaves = new ArrayList<>(ancestryPerLeaf.keySet());

    // very slow code, a bit of playing with project loom in an attempt to speed up is happening here
    int amountOfThreads = (leaves.size() / BATCH_SIZE) + 1;
    List<Callable<Integer>> tasks = IntStream.range(0, amountOfThreads)
        .mapToObj(i -> (Callable<Integer>) new WithElephantSolutionTask(i, ancestryPerLeaf))
        .toList();

    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      List<Future<Integer>> results = executor.invokeAll(tasks);
      for (Future<Integer> future : results) {
        maxResult = Math.max(maxResult, future.get());
      }
      return maxResult;
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private void calculateDistances() {
    nodesByName.values().forEach(graphNode -> {
      Queue<DistanceHelper> queue = new LinkedList<>();
      Map<String, Integer> distances = new HashMap<>();
      distances.put(graphNode.getName(), 0);

      for (GraphNode neighbour : graphNode.getNeighbours()) {
        queue.add(new DistanceHelper(neighbour.getName(), 1));
        distances.put(neighbour.getName(), 1);
      }

      while (!queue.isEmpty()) {
        DistanceHelper helper = queue.poll();
        for (GraphNode neighbour : nodesByName.get(helper.to).getNeighbours()) {
          if (distances.containsKey(neighbour.getName())) {
            continue;
          }
          queue.add(new DistanceHelper(neighbour.getName(), helper.cost + 1));
          distances.put(neighbour.getName(), helper.cost + 1);
        }
      }

      distancesFromTo.put(graphNode.getName(), distances);
    });
  }

  private record DistanceHelper(String to, int cost) {
  }

  private record PointsPotentialHelper(String node, int timeToOpenNode) {
  }
}
