package day5;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
  public static void main(String[] args) throws IOException {
    List<Stack<Character>> stacksPuzzle1 = readInputStacksPuzzle1();
    List<Stack<Character>> stacksPuzzle2 = readInputStacksPuzzle1();
    List<Command> commands = readInputCommandsPuzzle1();

    commands.forEach(command -> executeCommandPuzzle1(stacksPuzzle1, command));
    commands.forEach(command -> executeCommandPuzzle2(stacksPuzzle2, command));

    String result1 = stacksPuzzle1.stream().map(Stack::peek).map(c -> c + "").collect(joining(""));
    String result2 = stacksPuzzle2.stream().map(Stack::peek).map(c -> c + "").collect(joining(""));

    System.out.printf("Puzzle1: %s%n", result1);
    System.out.printf("Puzzle2: %s%n", result2);
  }

  private static void executeCommandPuzzle1(List<Stack<Character>> stacks, Command command) {
    for(int i = 0; i < command.amount(); i++) {
      char from = stacks.get(command.from()).pop();
      stacks.get(command.to()).push(from);
    }
  }

  private static void executeCommandPuzzle2(List<Stack<Character>> stacks, Command command) {
    Stack<Character> temp = new Stack<>();
    for(int i = 0; i < command.amount(); i++) {
      char from = stacks.get(command.from()).pop();
      temp.push(from);
    }

    for(int i = 0; i < command.amount(); i++) {
      char from = temp.pop();
      stacks.get(command.to()).push(from);
    }
  }

  private static List<Command> readInputCommandsPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day5", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    List<Command> commands = new ArrayList<>();

    int indexOfWhiteLine = findWhiteLine(lines);
    for(int i = indexOfWhiteLine+1; i < lines.size(); i++) {
      commands.add(new Command(lines.get(i)));
    }

    return commands;
  }

  private static List<Stack<Character>> readInputStacksPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day5", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    List<Stack<Character>> stacks = new ArrayList<>();
    
    int indexOfWhiteLine = findWhiteLine(lines);
    String stackLine = lines.get(indexOfWhiteLine -1).trim();
    String[] stackNumbers = stackLine.split(" {3}");

    int indexOfLastContainerLine = indexOfWhiteLine - 2;
    for (int i = 0; i < stackNumbers.length; i++) {
      Stack<Character> stack = new Stack<>();

      for(int j = indexOfLastContainerLine; j >= 0; j--) {
        int indexOfContainer = 4*i + 1;
        if(indexOfContainer < lines.get(j).length() && lines.get(j).charAt(indexOfContainer) != ' '){
          stack.push(lines.get(j).charAt(indexOfContainer));
        }else {
          break;
        }
      }

      stacks.add(stack);
    }
    return stacks;
  }

  private static int findWhiteLine(List<String> lines) {
    for(int i = 0; i < lines.size(); i++) {
      if(lines.get(i).isBlank()) {
        return i;
      }
    }
    return -1;
  }
}
