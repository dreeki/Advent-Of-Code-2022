package day7;

import java.util.ArrayList;
import java.util.List;

class LsCommand extends Command {

  private List<String> lines;

  public LsCommand() {
    lines = new ArrayList<>();
  }

  @Override
  void addOutput(String line) {
    lines.add(line);
  }

  @Override
  Node executeOn(Node node) {
    lines.forEach(line -> executeLine(line, node));

    return node;
  }

  private void executeLine(String line, Node node) {
    String[] splitted = line.split(" ");
    if(splitted[0].equals("dir")) {
      createDirectory(splitted[1], node);
    }else {
      createFile(splitted[1], splitted[0], node);
    }
  }

  private void createFile(String name, String size, Node node) {
    node.addFile(name, Long.parseLong(size));
  }

  private void createDirectory(String name, Node node) {
    node.addDir(name);
  }
}
