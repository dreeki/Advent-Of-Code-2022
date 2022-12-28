package day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    Puzzle puzzle = readInputPuzzle1Jungle();
    puzzle.jungle.executeCommands(puzzle.commands);
    System.out.printf("Puzzle1: %d%n", puzzle.jungle.calculatePassword());

    Puzzle puzzle2 = readInputPuzzle1Jungle();
    puzzle2.jungle.executeCommandsPart2(puzzle.commands);
    System.out.printf("Puzzle2: %d%n", puzzle2.jungle.calculatePassword());
  }

  private static Puzzle readInputPuzzle1Jungle() throws IOException {
    Path path = Path.of("src", "main", "resources", "day22", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    List<String> jungleLines = lines.stream().limit(lines.size() - 2).toList();
    Jungle jungle = new Jungle(jungleLines);

    String commandLine = lines.get(lines.size() - 1);
    List<Command> commands = new ArrayList<>();

    StringBuilder tempContainer = new StringBuilder();
    for(int i = 0; i < commandLine.length(); i++) {
      char c = commandLine.charAt(i);
      if(c == 'R' || c == 'L') {
        if(!tempContainer.isEmpty()) {
          commands.add(new MoveCommand(Integer.parseInt(tempContainer.toString())));
        }
        tempContainer = new StringBuilder();
        commands.add(new TurnCommand(c));
      }else {
        tempContainer.append(c);
      }
    }
    if(!tempContainer.isEmpty()) {
      commands.add(new MoveCommand(Integer.parseInt(tempContainer.toString())));
    }

    return new Puzzle(jungle, commands);
  }

  private record Puzzle(Jungle jungle, List<Command> commands) {
  }
}
