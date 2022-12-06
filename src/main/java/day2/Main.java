package day2;

import static day2.Main.Outcome.DRAW;
import static day2.Main.Outcome.LOSS;
import static day2.Main.Outcome.WIN;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    List<FirstRoundPuzzle> firstPuzzleRounds = readFirstRounds();
    int firstRoundPoints = firstPuzzleRounds.stream().mapToInt(FirstRoundPuzzle::getPoints).sum();
    List<SecondRoundPuzzle> secondPuzzleRounds = readSecondRounds();
    int secondRoundPoints = secondPuzzleRounds.stream().mapToInt(SecondRoundPuzzle::getPoints).sum();

    System.out.printf("Puzzle1: %d%n", firstRoundPoints);
    System.out.printf("Puzzle2: %d%n", secondRoundPoints);
  }

  private static List<FirstRoundPuzzle> readFirstRounds() throws IOException {
    Path path = Path.of("src", "main", "resources", "day2", "puzzle1.txt");
    return Files.lines(path)
        .map(FirstRoundPuzzle::new)
        .toList();
  }

  private static List<SecondRoundPuzzle> readSecondRounds() throws IOException {
    Path path = Path.of("src", "main", "resources", "day2", "puzzle1.txt");
    return Files.lines(path)
        .map(SecondRoundPuzzle::new)
        .toList();
  }

  public interface Round{
    int getPoints();
  }

  private record SecondRoundPuzzle(Type enemy, Outcome yours){

    private SecondRoundPuzzle {
    }

    private SecondRoundPuzzle(String line) {
      this(Type.enemyFromChar(line.charAt(0)), Outcome.fromInput(line.charAt(2)));
    }

    public int getPoints() {
      return yours.points + typePoints();
    }

    private int typePoints() {
      if(yours == DRAW) {
        return enemy.points;
      }
      if(yours == WIN) {
        return Arrays.stream(Type.values()).filter(yourType -> yourType.isWin(enemy)).findFirst().orElseThrow().points;
      }
      return Arrays.stream(Type.values()).filter(yourType -> enemy.isWin(yourType)).findFirst().orElseThrow().points;
    }
  }

  private record FirstRoundPuzzle(Type enemy, Type yours){

    private FirstRoundPuzzle {
    }

    private FirstRoundPuzzle(String line) {
      this(Type.enemyFromChar(line.charAt(0)), Type.yoursFromChar(line.charAt(2)));
    }

    public int getPoints() {
      return yours.getWinPoints(enemy) + yours.points;
    }
  }

  public enum Outcome {
    WIN(6),
    DRAW(3),
    LOSS(0);

    private final int points;

    Outcome(int points) {
      this.points = points;
    }

    public int getPoints() {
      return points;
    }

    private static Outcome fromInput(char c) {
      return switch (c) {
        case 'X' -> LOSS;
        case 'Y' -> DRAW;
        case 'Z' -> WIN;
        default -> throw new IllegalArgumentException();
      };
    }
  }

  public enum Type {
    ROCK(1){
      @Override boolean isWin(Type opponent) {
        return opponent == SCISSORS;
      }
      @Override int getWinPoints(Type opponent) {
        return switch (opponent) {
          case ROCK -> DRAW.getPoints();
          case PAPER -> LOSS.getPoints();
          case SCISSORS -> WIN.getPoints();
        };
      }
    },
    PAPER(2){
      @Override boolean isWin(Type opponent) {
        return opponent == ROCK;
      }
      @Override int getWinPoints(Type opponent) {
        return switch (opponent) {
          case ROCK -> WIN.getPoints();
          case PAPER -> DRAW.getPoints();
          case SCISSORS -> LOSS.getPoints();
        };
      }
    },
    SCISSORS(3){
      @Override boolean isWin(Type opponent) {
        return opponent == PAPER;
      }
      @Override int getWinPoints(Type opponent) {
        return switch (opponent) {
          case ROCK -> LOSS.getPoints();
          case PAPER -> WIN.getPoints();
          case SCISSORS -> DRAW.getPoints();
        };
      }
    };

    private final int points;

    Type(int points) {
      this.points = points;
    }

    abstract boolean isWin(Type opponent);
    abstract int getWinPoints(Type opponent);

    private static Type enemyFromChar(char c) {
      return switch (c) {
        case 'A' -> ROCK;
        case 'B' -> PAPER;
        case 'C' -> SCISSORS;
        default -> throw new IllegalArgumentException();
      };
    }

    private static Type yoursFromChar(char c) {
      return switch (c) {
        case 'X' -> ROCK;
        case 'Y' -> PAPER;
        case 'Z' -> SCISSORS;
        default -> throw new IllegalArgumentException();
      };
    }
  }
}
