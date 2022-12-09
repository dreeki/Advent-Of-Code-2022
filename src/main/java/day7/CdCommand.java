package day7;

class CdCommand extends Command {

  private final String location;

  public CdCommand(String location) {
    this.location = location;
  }

  @Override
  void addOutput(String line) {
    throw new UnsupportedOperationException();
  }

  @Override
  Node executeOn(Node node) {
    if(location.equals("/")) {
      return node.getRoot();
    }

    if(location.equals("..")) {
      return node.getParent();
    }

    return node.getChild(location);
  }
}
