package day21;


public class Node extends AbstractNode {
  private final char operator;

  private AbstractNode left;
  private AbstractNode right;

  public Node(String name, char operator) {
    super(name);
    this.operator = operator;
  }

  public void setLeft(AbstractNode left) {
    this.left = left;
    left.setParent(this);
  }

  public void setRight(AbstractNode right) {
    this.right = right;
    right.setParent(this);
  }

  @Override
  public long calculateValue() {
    return switch (operator) {
      case '+' -> left.calculateValue() + right.calculateValue();
      case '-' -> left.calculateValue() - right.calculateValue();
      case '*' -> left.calculateValue() * right.calculateValue();
      case '/' -> left.calculateValue() / right.calculateValue();
      default -> throw new UnsupportedOperationException();
    };
  }

  public long getValueForEquality(AbstractNode requester) {
    if(operator == '=') {
      if(left == requester) {
        return right.calculateValue();
      }else {
        return left.calculateValue();
      }
    }else {
      long resultToBecome = parent.getValueForEquality(this);
      if(left == requester) {
        long rightValue = right.calculateValue();
        return switch (operator) {
          case '+' -> resultToBecome - rightValue;
          case '-' -> resultToBecome + rightValue;
          case '*' -> resultToBecome / rightValue;
          case '/' -> resultToBecome * rightValue;
          default -> throw new UnsupportedOperationException();
        };
      }else {
        long leftValue = left.calculateValue();
        return switch (operator) {
          case '+' -> resultToBecome - leftValue;
          case '-' -> leftValue - resultToBecome;
          case '*' -> resultToBecome / leftValue;
          case '/' -> leftValue / resultToBecome;
          default -> throw new UnsupportedOperationException();
        };
      }
    }
  }
}
