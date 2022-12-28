package day19;

import static day19.Resource.GEODE;

import java.util.ArrayList;
import java.util.List;

public class Factory {

  private final Blueprint blueprint;
  private final int totalTime;
  private int maxGeodes = 0;

  public Factory(Blueprint blueprint, int totalTime) {
    this.blueprint = blueprint;
    this.totalTime = totalTime;
  }

  public void findMaxAmountOfGeodes() {
    int[] resources = new int[] {0, 0, 0, 0};
    int[] robots = new int []{1, 0, 0, 0};

    findBlueprintQuality(1, resources, robots);
  }

  private void findBlueprintQuality(int time, int[] resources, int[] robots) {
    int timeLeft = totalTime - time + 1;
    if(maxGeodes >= calculateMaxAmountOfGeodesInBranch(resources, robots, timeLeft)) {
      return ;
    }

    if(time > totalTime) {
      maxGeodes = Math.max(maxGeodes, resources[GEODE.getIndex()]);
      return ;
    }

    List<Integer> possibleRobotsToBuild = findPossibleRobotsToBuild(resources, robots);

//    if(time == 24 && resources[0] == 2 && resources[1] == 21 && resources[2] == 7 && resources[3] == 4) {
//      System.out.println();
//    }

    for(int r = 0; r < possibleRobotsToBuild.size(); r++) {
      int[] newResources = resources.clone();
      int[] newRobots = robots.clone();

      for(int i = 0; i < 4; i++) {
        newResources[i] = newResources[i] - blueprint.getRobotCosts()[possibleRobotsToBuild.get(r)][i] + robots [i];
      }
      newRobots[possibleRobotsToBuild.get(r)] = newRobots[possibleRobotsToBuild.get(r)] + 1;
      findBlueprintQuality(time + 1, newResources, newRobots);
    }

    if(!possibleRobotsToBuild.contains(GEODE.getIndex())) {
      int[] newResources = resources.clone();

      for(int i = 0; i < 4; i++) {
        newResources[i] = newResources[i] + robots[i];
      }
      findBlueprintQuality(time + 1, newResources, robots);
    }
  }

  private List<Integer> findPossibleRobotsToBuild(int[] resources, int[] robots) {
    boolean canBuildGeode = true;
    for(int i = 0; i < 3; i++) {
      if(blueprint.getRobotCosts()[GEODE.getIndex()][i] > resources[i]) {
        canBuildGeode = false;
      }
    }

    if(canBuildGeode) {
      return List.of(GEODE.getIndex());
    }

    List<Integer> result = new ArrayList<>();

    for(int r = 0; r < 3; r++) {
      boolean canBuildRobot = true;
      for(int i =0; i < 3; i++) {
        if(blueprint.getRobotCosts()[r][i] > resources[i]){
          canBuildRobot = false;
        }
      }
      if(canBuildRobot) {
        boolean shouldBuildResources = false;
        for(int i = 0; i < 4; i++) {
          if(blueprint.getRobotCosts()[i][r]*2 > resources[r]) {
            shouldBuildResources = true;
          }
        }

        boolean shouldBuildProduction = false;
        for(int i = 0; i < 4; i++) {
          if(blueprint.getRobotCosts()[i][r] > robots[r]) {
            shouldBuildProduction = true;
          }
        }

        if(shouldBuildResources && shouldBuildProduction) {
          result.add(r);
        }
      }
    }
//    if(canBuildGeode) {
//      result.add(GEODE.getIndex());
//    }
    return result;
  }

  private static int calculateMaxAmountOfGeodesInBranch(int[] resources, int[] robots, int timeLeft) {
    int timeForNewRobotsToProduce = timeLeft -1;

    int currentAmountOfGeodes = resources[GEODE.getIndex()];
    int productionCurrentRobots = robots[GEODE.getIndex()] * timeLeft;
    int maxNewCreatedRobotProduction = timeForNewRobotsToProduce * timeForNewRobotsToProduce - timeForNewRobotsToProduce;

    return currentAmountOfGeodes + productionCurrentRobots + maxNewCreatedRobotProduction;
  }

  public int getId() {
    return blueprint.getId();
  }

  public int getMaxGeodes() {
    return maxGeodes;
  }
}
