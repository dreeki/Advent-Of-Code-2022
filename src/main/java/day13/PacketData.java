package day13;

import static day13.OrderStatus.IDK;
import static day13.OrderStatus.RIGHT;
import static day13.OrderStatus.WRONG;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

sealed interface PacketData permits PacketDataInteger, PacketDataList {

  default boolean isCorrectOrder(PacketData packetData) {
    return this.orderStatus(packetData).equals(RIGHT);
  }

  OrderStatus orderStatus(PacketData packetData);

  default OrderStatus orderStatus(PacketDataInteger val1, PacketDataInteger val2) {
    if (val1.data() < val2.data()) {
      return RIGHT;
    }
    if (val1.data() > val2.data()) {
      return WRONG;
    }
    return IDK;
  }

  default OrderStatus orderStatus(PacketDataList list1, PacketDataList list2) {
    if (list1.data().size() < list2.data().size()) {
      for (int i = 0; i < list1.data().size(); i++) {
        OrderStatus status = list1.data().get(i).orderStatus(list2.data().get(i));
        if (status.equals(IDK)) {
          continue;
        }
        return status;
      }
      return RIGHT;
    }
    if (list1.data().size() > list2.data().size()) {
      for (int i = 0; i < list2.data().size(); i++) {
        OrderStatus status = list1.data().get(i).orderStatus(list2.data().get(i));
        if (status.equals(IDK)) {
          continue;
        }
        return status;
      }
      return WRONG;
    }
    if (list1.data().size() == list2.data().size()) {
      for (int i = 0; i < list1.data().size(); i++) {
        OrderStatus status = list1.data().get(i).orderStatus(list2.data().get(i));
        if (status.equals(IDK)) {
          continue;
        }
        return status;
      }
      return IDK;
    }
    throw new IllegalArgumentException();
  }

  static PacketData fromLine(String line) {
    if (line.isBlank()) {
      return new PacketDataList(emptyList());
    }
    if (!line.contains("[")) {
      return new PacketDataInteger(Integer.parseInt(line));
    }
    if (line.startsWith("[")) {
      int indexOfCloseCharacter = line.length() - 1;
      String content = line.substring(1, indexOfCloseCharacter);
      String[] splitted = content.split(",");

      List<Integer> arrayIndexes = findArrayIndexes(splitted);

      List<ArrayRange> arrayArrayRanges = new ArrayList<>(arrayIndexes.size() / 2);
      for (int i = 0; i < arrayIndexes.size(); i += 2) {
        arrayArrayRanges.add(new ArrayRange(arrayIndexes.get(i), arrayIndexes.get(i + 1)));
      }

      if (arrayArrayRanges.isEmpty()) {
        List<PacketData> packetData = Arrays.stream(splitted).map(PacketData::fromLine).toList();
        return new PacketDataList(packetData);
      } else {
        List<PacketData> packetData = new ArrayList<>();
        for (int i = 0; i < arrayArrayRanges.get(0).index1; i++) {
          packetData.add(PacketData.fromLine(splitted[i]));
        }
        for (int i = 0; i < arrayArrayRanges.size() - 1; i++) {
          packetData.add(PacketData.fromLine(arrayArrayRanges.get(i).appendWithComma(splitted)));
          for (int j = arrayArrayRanges.get(i).index2 + 1; j < arrayArrayRanges.get(i).index1; j++) {
            packetData.add(PacketData.fromLine(splitted[j]));
          }
        }
        packetData.add(PacketData.fromLine(arrayArrayRanges.get(arrayArrayRanges.size() - 1).appendWithComma(splitted)));
        for (int i = arrayArrayRanges.get(arrayArrayRanges.size() - 1).index2 + 1; i < splitted.length; i++) {
          packetData.add(PacketData.fromLine(splitted[i]));
        }
        return new PacketDataList(packetData);
      }
    }
    throw new InputMismatchException();
  }

  private static List<Integer> findArrayIndexes(String[] splitted) {
    List<Integer> arrayIndexes = new ArrayList<>();
    int amountOfOpens = 0;
    for (int i = 0; i < splitted.length; i++) {
      if (splitted[i].contains("[")) {
        if (arrayIndexes.size() % 2 == 0) {
          arrayIndexes.add(i);
        }
        amountOfOpens += splitted[i].chars().filter(c -> c == '[').count();
      }
      if (splitted[i].contains("]")) {
        amountOfOpens -= splitted[i].chars().filter(c -> c == ']').count();
        if (amountOfOpens == 0) {
          arrayIndexes.add(i);
        }
      }
    }
    return arrayIndexes;
  }

  record ArrayRange(int index1, int index2) {
    String appendWithComma(String[] splitted) {
      String result = splitted[index1];
      for (int i = index1 + 1; i <= index2; i++) {
        result += "," + splitted[i];
      }
      return result;
    }
  }
}