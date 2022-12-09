package day7;

abstract class Command {
  abstract void addOutput(String line);

  abstract Node executeOn(Node node);
}
