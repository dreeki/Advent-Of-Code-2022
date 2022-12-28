package day21;

import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class Main {

  public static void main(String[] args) throws IOException {
    AbstractNode treeRootPuzzle1 = readInputPuzzle1();
    Leaf humanLeafPuzzle2 = readInputPuzzle2();

    System.out.printf("Puzzle1: %d%n", treeRootPuzzle1.calculateValue());
    System.out.printf("Puzzle2: %d%n", humanLeafPuzzle2.getValueForEquality());
  }

  private static AbstractNode readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day21", "puzzle1.txt");
    Map<String, String> contentByName = Files.lines(path).map(line -> line.split(": ")).collect(toMap(splitted -> splitted[0], splitted -> splitted[1]));

    Map<String, AbstractNode> nodesByName = contentByName.entrySet().stream().collect(toMap(Entry::getKey, entry -> createNode(entry.getKey(), entry.getValue())));

    String content = contentByName.get("root");
    AbstractNode root = createNode("root", content);

    Queue<AbstractNode> queue = new LinkedList<>();
    queue.add(root);

    while(!queue.isEmpty()) {
      AbstractNode current = queue.poll();
      if(current instanceof Node currentNode) {
        String[] currentContentSplitted = contentByName.get(current.getName()).split(" ");

        AbstractNode left = nodesByName.get(currentContentSplitted[0]);
        currentNode.setLeft(left);
        queue.add(left);

        AbstractNode right = nodesByName.get(currentContentSplitted[2]);
        currentNode.setRight(right);
        queue.add(right);
      }
    }

    return root;
  }

  private static Leaf readInputPuzzle2() throws IOException {
    Path path = Path.of("src", "main", "resources", "day21", "puzzle1.txt");
    Map<String, String> contentByName = Files.lines(path).map(line -> line.split(": ")).collect(toMap(splitted -> splitted[0], splitted -> splitted[1]));

    Map<String, AbstractNode> nodesByName = contentByName.entrySet().stream().collect(toMap(Entry::getKey, entry -> createNode(entry.getKey(), entry.getValue())));

    AbstractNode root = new Node("root", '=');

    Queue<AbstractNode> queue = new LinkedList<>();
    queue.add(root);

    while(!queue.isEmpty()) {
      AbstractNode current = queue.poll();
      if(current instanceof Node currentNode) {
        String[] currentContentSplitted = contentByName.get(current.getName()).split(" ");

        AbstractNode left = nodesByName.get(currentContentSplitted[0]);
        currentNode.setLeft(left);
        queue.add(left);

        AbstractNode right = nodesByName.get(currentContentSplitted[2]);
        currentNode.setRight(right);
        queue.add(right);
      }
    }

    return (Leaf) nodesByName.get("humn");
  }

  private static AbstractNode createNode(String name, String content) {
    String[] contentSplitted = content.split(" ");
    if (isLeaf(contentSplitted)) {
      return new Leaf(name, Long.parseLong(contentSplitted[0]));
    }else {
      return new Node(name, contentSplitted[1].charAt(0));
    }
  }

  private static boolean isLeaf(String[] contentSplitted) {
    return contentSplitted.length == 1;
  }
}
