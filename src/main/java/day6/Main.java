package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Main {
  private static final int START_OF_PACKET_MARKER_SIZE = 4;
  private static final int START_OF_MESSAGE_MARKER_SIZE = 14;
  public static void main(String[] args) throws IOException {
    String text = readInputPuzzle1();

    int firstMarker = START_OF_PACKET_MARKER_SIZE;
    for(int i = 0; i < text.length() - START_OF_PACKET_MARKER_SIZE; i++) {
      Set<Character> chars = new HashSet<>(START_OF_PACKET_MARKER_SIZE);
      for(int j = 0; j < START_OF_PACKET_MARKER_SIZE; j++) {
        chars.add(text.charAt(i+j));
      }
      if(chars.size() == START_OF_PACKET_MARKER_SIZE) {
        break;
      }else {
        firstMarker++;
      }
    }

    int secondMarker = START_OF_MESSAGE_MARKER_SIZE;
    for(int i = 0; i < text.length() - START_OF_MESSAGE_MARKER_SIZE; i++) {
      Set<Character> chars = new HashSet<>(START_OF_MESSAGE_MARKER_SIZE);
      for(int j = 0; j < START_OF_MESSAGE_MARKER_SIZE; j++) {
        chars.add(text.charAt(i+j));
      }
      if(chars.size() == START_OF_MESSAGE_MARKER_SIZE) {
        break;
      }else {
        secondMarker++;
      }
    }

    System.out.printf("Puzzle1: %d%n", firstMarker);
    System.out.printf("Puzzle2: %d%n", secondMarker);
  }

  private static String readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day6", "puzzle1.txt");
    return Files.lines(path).findFirst().get();
  }
}
