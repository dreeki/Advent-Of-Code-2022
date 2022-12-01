package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    List<Elf> elves = readElves();

    long maxCalories = elves.stream().mapToLong(Elf::totalCalories).max().getAsLong();
    long top3MaxCalories = elves.stream().map(Elf::totalCalories).sorted(Comparator.reverseOrder()).limit(3).mapToLong(i -> i).sum();

    System.out.printf("Puzzle1: %d%n", maxCalories);
    System.out.printf("Puzzle2: %d%n", top3MaxCalories);
  }

  private static List<Elf> readElves() throws IOException {
    Path path = Path.of("src", "main", "resources", "day1", "puzzle1.txt");
    List<String> lines = Files.readAllLines(path);

    List<Elf> elves = new ArrayList<>();

    List<Integer> foodItems = new ArrayList<>();
    for (String line : lines) {
      if(line.isBlank()) {
        elves.add(new Elf(foodItems));
        foodItems = new ArrayList<>();
      }else {
        foodItems.add(Integer.parseInt(line));
      }
    }
    return elves;
  }


}
