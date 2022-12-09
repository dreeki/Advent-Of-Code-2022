package day4;

import java.util.List;
import java.util.stream.IntStream;

record Elf(List<Integer> sectionAssignments) {
  Elf(String line) {
    this(findAssignments(line));
  }

  public boolean hasAnyOverlap(Elf elf) {
    return sectionAssignments.stream().anyMatch(elf.sectionAssignments::contains);
  }

  public boolean contains(Elf elf) {
    return sectionAssignments.containsAll(elf.sectionAssignments);
  }

  private static List<Integer> findAssignments(String line) {
    String[] splitted = line.split("-");
    return IntStream.rangeClosed(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]))
        .boxed()
        .toList();
  }
}
