package day13;

import java.util.List;

public record PacketDataInteger(int data) implements PacketData {
  @Override
  public OrderStatus orderStatus(PacketData packetData) {
    return switch (packetData) {
      case PacketDataInteger packetDataInteger -> orderStatus(this, packetDataInteger);
      case PacketDataList packetDataList -> orderStatus(asList(), packetDataList);
    };
  }

  public PacketDataList asList() {
    return new PacketDataList(List.of(this));
  }
}
