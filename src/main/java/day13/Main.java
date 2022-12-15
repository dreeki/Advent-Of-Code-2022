package day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

  private static final String DIVIDER_ONE = "[[2]]";
  private static final String DIVIDER_TWO = "[[6]]";

  public static void main(String[] args) throws IOException {
    List<Pair> pairs = readInputPuzzle1();
    int result1 = pairs.stream().filter(Pair::isRightOrder).mapToInt(Pair::index).sum();

    List<PacketData> packetData = readInputPuzzle2();
    List<PacketData> sorted = sort(packetData);

    int index1 = sorted.indexOf(PacketData.fromLine(DIVIDER_ONE)) + 1;
    int index2 = sorted.indexOf(PacketData.fromLine(DIVIDER_TWO)) + 1;

    System.out.printf("Puzzle1: %d%n", result1);
    System.out.printf("Puzzle2: %d%n", index1*index2);
  }

  private static List<PacketData> sort(List<PacketData> packetData) {
    List<PacketData> result = new ArrayList<>(packetData);
    for (int i = 0; i < result.size(); i++) {
      for (int j = 0; j < result.size() - (i + 1); j++) {
        if(!result.get(j).isCorrectOrder(result.get(j+1))) {
          PacketData temp = result.get(j);
          result.set(j, result.get(j+1));
          result.set(j+1, temp);
        }
      }
    }
    return result;
  }

  private static List<Pair> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day13", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    List<Pair> result = new ArrayList<>();

    for (int i = 0; i < lines.size(); i += 3) {
      result.add(new Pair((i / 3) + 1, lines.get(i), lines.get(i + 1)));
    }

    return result;
  }

  private static List<PacketData> readInputPuzzle2() throws IOException {
    Path path = Path.of("src", "main", "resources", "day13", "puzzle1.txt");
    return Stream.concat(
            Files.lines(path),
            Stream.of(DIVIDER_ONE, DIVIDER_TWO))
        .filter(line -> !line.isBlank())
        .map(PacketData::fromLine)
        .toList();
  }
}
