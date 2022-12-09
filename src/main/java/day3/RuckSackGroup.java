package day3;

import java.util.List;
import java.util.function.IntPredicate;

record RuckSackGroup(List<String> ruckSacks) {
  public int findPriority() {
    return calculatePriority(findCommonType());
  }

  private char findCommonType() {
    return (char) ruckSacks.get(0).chars()
        .filter(inAllRuckSacks())
        .findFirst()
        .orElseThrow();
  }

  private IntPredicate inAllRuckSacks() {
    return character -> ruckSacks.stream()
        .mapToInt(content -> content.indexOf(character))
        .allMatch(val -> val != -1);
  }

  private int calculatePriority(char commonType) {
    if(lowerCase(commonType)) {
      return commonType - 'a' + 1;
    }else {
      return commonType - 'A' + 27;
    }
  }

  private boolean lowerCase(char commonType) {
    return commonType >= 'a' && commonType <= 'z';
  }
}
