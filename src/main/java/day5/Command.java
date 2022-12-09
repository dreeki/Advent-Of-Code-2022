package day5;

record Command(int amount, int from, int to) {
  Command(String line) {
    this(Integer.parseInt(line.split(" ")[1]), Integer.parseInt(line.split(" ")[3]), Integer.parseInt(line.split(" ")[5]));
  }

  @Override
  public int from() {
    return from-1;
  }

  @Override
  public int to() {
    return to-1;
  }
}
