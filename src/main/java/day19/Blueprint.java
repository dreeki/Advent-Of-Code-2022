package day19;

public class Blueprint {
  private final int id;
  private final int[][] robotCosts;

  public Blueprint(String line) {
    String[] splitted = line.split(": ");
    id = Integer.parseInt(splitted[0].split(" ")[1]);

    String[] robotParts = splitted[1].split("\\.");
    robotCosts = new int[][] {
        new int[] {Integer.parseInt(robotParts[0].trim().split(" ")[4]), 0, 0, 0},
        new int[] {Integer.parseInt(robotParts[1].trim().split(" ")[4]), 0, 0, 0},
        new int[] {Integer.parseInt(robotParts[2].trim().split(" ")[4]), Integer.parseInt(robotParts[2].trim().split(" ")[7]), 0, 0},
        new int[] {Integer.parseInt(robotParts[3].trim().split(" ")[4]), 0, Integer.parseInt(robotParts[3].trim().split(" ")[7]), 0},
    };
  }

  public int getId() {
    return id;
  }

  public int[][] getRobotCosts() {
    return robotCosts;
  }
}
