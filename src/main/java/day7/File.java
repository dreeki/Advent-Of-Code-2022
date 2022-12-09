package day7;

import static java.util.Collections.emptyList;

import java.util.Collection;

class File extends Node {

  private final long size;

  File(Node parent, String name, long size) {
    super(parent, name);
    this.size = size;
  }

  @Override
  Collection<Node> getChildren() {
    return emptyList();
  }

  @Override
  Node getChild(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  void addDir(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  void addFile(String name, long size) {
    throw new UnsupportedOperationException();
  }

  @Override
  long calculateSize() {
    return size;
  }
}
