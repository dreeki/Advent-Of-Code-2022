package day13;

import java.util.List;

public record PacketDataList(List<PacketData> data) implements PacketData {

  @Override
  public OrderStatus orderStatus(PacketData packetData) {
    return switch (packetData) {
      case PacketDataInteger packetDataInteger -> orderStatus(this, packetDataInteger.asList());
      case PacketDataList packetDataList -> orderStatus(this, packetDataList);
    };
  }
}
