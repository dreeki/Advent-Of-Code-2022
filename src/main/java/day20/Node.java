package day20;

public class Node {
  private final long value;
  private Node previous;
  private Node next;

  public Node(long value) {
    this.value = value;
  }

  public void setNext(Node next) {
    this.next = next;
    next.previous = this;
  }

  public void move(long number) {
    int direction = number > 0 ? 1 : -1;

    long distance = Math.abs(number);
    for(int i = 0; i < distance; i++) {
      if(direction == 1) {
        swapWithNext();
      }else {
        swapWithPrevious();
      }
    }
  }

  private void swapWithNext() {
    Node currentNext = next;
    Node newNext = next.next;
    Node oldPrevious = previous;

    previous = currentNext;
    next = newNext;

    currentNext.next = this;
    currentNext.previous = oldPrevious;

    oldPrevious.next = currentNext;
    newNext.previous = this;
  }

  private void swapWithPrevious() {
    Node currentPrevious = previous;
    Node newPrevious = previous.previous;
    Node oldNext = next;

    previous = newPrevious;
    next = currentPrevious;

    currentPrevious.next = oldNext;
    currentPrevious.previous = this;

    oldNext.previous = currentPrevious;
    newPrevious.next = this;
  }

  public long findNumberAtRelativePosition(int position) {
    Node current = this;
    for(int i = 0; i < position; i++) {
      current = current.next;
    }
    return current.value;
  }

  public long getValue() {
    return value;
  }

  public Node getNext() {
    return next;
  }

  @Override
  public String toString() {
    return value + "";
  }
}
