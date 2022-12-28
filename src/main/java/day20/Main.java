package day20;

import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

public class Main {

  private static final long DECRYPTION_KEY = 811589153;

  public static void main(String[] args) throws IOException {
    executePuzzle1();
    System.out.println();
    executePuzzle2();
  }

  private static void executePuzzle1() throws IOException {
    List<Long> numbers = readInputPuzzle1();
    Map<Long, Node> nodesByOriginalPosition = LongStream.range(0, numbers.size()).boxed().collect(toMap(i -> i, i -> new Node(numbers.get(i.intValue()))));

    for (long i = 0; i < numbers.size(); i++) {
      Node current = nodesByOriginalPosition.get(i);
      Node next = nodesByOriginalPosition.get((i + 1) % numbers.size());
      current.setNext(next);
    }

    for (long i = 0; i < numbers.size(); i++) {
      Node node = nodesByOriginalPosition.get(i);
      node.move(node.getValue() % (numbers.size()-1));
    }

    long originalPositionOfNumberZero = LongStream.range(0, numbers.size()).filter(i -> numbers.get((int) i) == 0).findFirst().getAsLong();
    Node numberZero = nodesByOriginalPosition.get(originalPositionOfNumberZero);

    long val1 = numberZero.findNumberAtRelativePosition(1000);
    long val2 = numberZero.findNumberAtRelativePosition(2000);
    long val3 = numberZero.findNumberAtRelativePosition(3000);

    System.out.printf("Puzzle1: %d%n", val1 + val2 + val3);
  }

  private static void executePuzzle2() throws IOException {
    List<Long> numbers = readInputPuzzle2();
    Map<Long, Node> nodesByOriginalPosition = LongStream.range(0, numbers.size()).boxed().collect(toMap(i -> i, i -> new Node(numbers.get(i.intValue()))));

    for (long i = 0; i < numbers.size(); i++) {
      Node current = nodesByOriginalPosition.get(i);
      Node next = nodesByOriginalPosition.get((i + 1) % numbers.size());
      current.setNext(next);
    }

    for (int x = 0; x < 10; x++) {
      for (long i = 0; i < numbers.size(); i++) {
        Node node = nodesByOriginalPosition.get(i);
        node.move(node.getValue() % (numbers.size()-1));
      }
    }

    long originalPositionOfNumberZero = LongStream.range(0, numbers.size()).filter(i -> numbers.get((int)i) == 0).findFirst().getAsLong();
    Node numberZero = nodesByOriginalPosition.get(originalPositionOfNumberZero);

    long val1 = numberZero.findNumberAtRelativePosition(1000);
    long val2 = numberZero.findNumberAtRelativePosition(2000);
    long val3 = numberZero.findNumberAtRelativePosition(3000);

    System.out.printf("Puzzle2: %d%n", val1 + val2 + val3);
  }

  private static void printAll(List<Long> numbers, Map<Long, Node> nodesByOriginalPosition) {
    Node current = nodesByOriginalPosition.get((long)0);
    for(int i = 0; i < numbers.size(); i++) {
      System.out.print(current.getValue() + " ");
      current = current.getNext();
    }
    System.out.println();
  }

  private static List<Long> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day20", "puzzle1.txt");
    return Files.lines(path)
        .map(Long::parseLong)
        .toList();
  }

  private static List<Long> readInputPuzzle2() throws IOException {
    Path path = Path.of("src", "main", "resources", "day20", "puzzle1.txt");
    return Files.lines(path)
        .map(Long::parseLong)
        .map(l -> l * DECRYPTION_KEY)
        .toList();
  }
}
