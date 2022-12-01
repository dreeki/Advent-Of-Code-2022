package day1;

import java.util.List;

public record Elf(List<Integer> foodItems) {

  public long totalCalories() {
    return foodItems.stream()
        .mapToLong(i -> i)
        .sum();
  }
}
