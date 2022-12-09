package day7;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {

  private static final long MAX_SIZE = 100_000;

  private static final long TOTAL_DISK_SPACE = 70_000_000;
  private static final long REQUIRED_DISK_SPACE = 30_000_000;


  public static void main(String[] args) throws IOException {
    List<Command> commands = readInputPuzzle1();

    Node currentNode = new Directory(null, "/");
    for(Command command: commands) {
      currentNode = command.executeOn(currentNode);
    }

    currentNode = currentNode.getRoot();

    Set<Node> directories = findAllDirectories(currentNode);
    List<Long> directorySizes = directories.stream().map(Node::calculateSize).toList();
    long result1 = directorySizes.stream().mapToLong(i -> i).filter(val -> val <= MAX_SIZE).sum();

    long ocupiedDiskSpace = currentNode.calculateSize();
    long currentFreeDiskSpace = TOTAL_DISK_SPACE - ocupiedDiskSpace;
    long minimalDiskSpaceToFree = REQUIRED_DISK_SPACE - currentFreeDiskSpace;

    long result2 = directorySizes.stream().mapToLong(i -> i).filter(val -> val >= minimalDiskSpaceToFree).min().orElseThrow();

    System.out.printf("Puzzle1: %d%n", result1);
    System.out.printf("Puzzle2: %d%n", result2);
  }

  private static Set<Node> findAllDirectories(Node currentNode) {
    List<Node> directories = currentNode.getChildren().stream().filter(node -> node instanceof Directory).toList();
    Set<Node> result = new HashSet<>(directories);
    result.add(currentNode);

    for (Node directory : directories) {
      Set<Node> childDirectories = findAllDirectories(directory);
      result.addAll(childDirectories);
    }

    return result;
  }

  private static List<Command> readInputPuzzle1() throws IOException {
    Path path = Path.of("src", "main", "resources", "day7", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    List<Command> commands = new ArrayList<>();

    Command command = null;
    for (String line : lines) {
      String[] splitted = line.split(" ");
      if(splitted[0].equals("$")) {
        if(nonNull(command)) {
          commands.add(command);
        }
        if(splitted[1].equals("cd")) {
          command = new CdCommand(splitted[2]);
        }else {
          command = new LsCommand();
        }
      }else if(nonNull(command)) {
        command.addOutput(line);
      }
    }
    commands.add(command);

    return commands;
  }
}
