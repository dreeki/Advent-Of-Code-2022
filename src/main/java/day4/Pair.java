package day4;

record Pair(Elf elf1, Elf elf2) {
  Pair(String line) {
    this(new Elf(line.split(",")[0]), new Elf(line.split(",")[1]));
  }

  public boolean hasAnyOverlap() {
    return elf1.hasAnyOverlap(elf2);
  }

  public boolean isFullyContaining() {
    return elf1.contains(elf2) || elf2.contains(elf1);
  }

}
