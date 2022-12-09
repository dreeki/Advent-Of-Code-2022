package day7;

import static java.util.Objects.isNull;

import java.util.Collection;

abstract class Node {

  final String name;
  Node parent;

  Node(String name) {
    this.name = name;
  }

  Node(Node parent, String name) {
    this(name);
    this.parent = parent;
  }

  Node getParent() {
    return parent;
  }

  Node getRoot() {
    if (isNull(parent)) {
      return this;
    }
    return parent.getRoot();
  }

  abstract Collection<Node> getChildren();

  abstract Node getChild(String name);

  abstract void addDir(String name);

  abstract void addFile(String name, long size);

  abstract long calculateSize();
}
