package day7;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class Directory extends Node {

  private Map<String, Node> children;

  public Directory(Node parent, String name) {
    super(parent, name);
    this.children = new HashMap<>();
  }

  @Override
  Collection<Node> getChildren() {
    return children.values();
  }

  @Override
  Node getChild(String name) {
    return children.get(name);
  }

  @Override
  void addDir(String name) {
    children.put(name, new Directory(this, name));
  }

  @Override
  void addFile(String name, long size) {
    children.put(name, new File(this, name, size));
  }

  @Override
  long calculateSize() {
    return children.values().stream().mapToLong(Node::calculateSize).sum();
  }
}
