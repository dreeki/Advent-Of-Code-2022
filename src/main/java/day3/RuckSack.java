package day3;

record RuckSack(String compartiment1, String compartiment2) {
  RuckSack(String line) {
    this(line.substring(0, line.length() / 2), line.substring(line.length() / 2));
  }

  public int findPriority() {
    return calculatePriority(findCommonType());
  }

  private char findCommonType() {
    return (char) compartiment1.chars()
        .filter(val -> compartiment2.indexOf((char)val) != -1)
        .findFirst()
        .orElseThrow();
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
