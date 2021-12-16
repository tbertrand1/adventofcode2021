package fr.t12.adventofcode.days;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

public class Day16 extends Day<String, Integer, Long> {

    public Day16() {
        super(16);
    }

    private static String hexToBinary(String value) {
        StringBuilder binary = new StringBuilder();
        for (char c : value.toCharArray()) {
            String charBinary = Integer.toBinaryString(Integer.parseInt(Character.toString(c), 16));
            binary.append(StringUtils.leftPad(charBinary, 4, "0"));
        }
        return binary.toString();
    }

    private static long binaryToLong(String value) {
        return Long.parseLong(value, 2);
    }

    @Override
    protected String getInput() {
        return readInputAsString();
    }

    @Override
    protected Integer resolvePart1(String input) {
        String inputBinary = hexToBinary(input);
        Packet packet = Packet.parse(inputBinary, new MutableInt(0));
        return packet.calculateSumVersions();
    }

    @Override
    protected Long resolvePart2(String input) {
        String inputBinary = hexToBinary(input);
        Packet packet = Packet.parse(inputBinary, new MutableInt(0));
        return packet.calculateValue();
    }

    static final class Packet {
        private final List<Packet> subPackets = new ArrayList<>();
        private int version;
        private int type;
        private Long value;

        public static Packet parse(String inputBinary, MutableInt position) {
            Packet packet = new Packet();
            packet.parseVersion(inputBinary, position);
            packet.parseType(inputBinary, position);
            if (packet.type == 4) {
                packet.parseValue(inputBinary, position);
            } else {
                packet.parseSubPackets(inputBinary, position);
            }
            return packet;
        }

        private static String readBits(String binary, MutableInt position, int size) {
            String bits = binary.substring(position.intValue(), position.intValue() + size);
            position.setValue(position.intValue() + size);
            return bits;
        }

        private void parseVersion(String inputBinary, MutableInt position) {
            this.version = (int) binaryToLong(readBits(inputBinary, position, 3));
        }

        private void parseType(String inputBinary, MutableInt position) {
            this.type = (int) binaryToLong(readBits(inputBinary, position, 3));
        }

        private void parseValue(String binary, MutableInt position) {
            StringBuilder packetValue = new StringBuilder();
            while (true) {
                long groupBit = binaryToLong(readBits(binary, position, 1));
                packetValue.append(readBits(binary, position, 4));
                if (groupBit == 0) {
                    break;
                }
            }
            this.value = binaryToLong(packetValue.toString());
        }

        private void parseSubPackets(String inputBinary, MutableInt position) {
            long lengthType = binaryToLong(readBits(inputBinary, position, 1));
            if (lengthType == 0) {
                long subPacketsLength = binaryToLong(readBits(inputBinary, position, 15));
                long subPacketsEnd = position.intValue() + subPacketsLength;
                while (position.intValue() != subPacketsEnd) {
                    this.subPackets.add(Packet.parse(inputBinary, position));
                }
            } else {
                long subPacketsCount = binaryToLong(readBits(inputBinary, position, 11));
                int indexSubPacket = 0;
                while (indexSubPacket < subPacketsCount) {
                    this.subPackets.add(Packet.parse(inputBinary, position));
                    indexSubPacket++;
                }
            }
        }

        public int calculateSumVersions() {
            return this.version + this.subPackets.stream().map(Packet::calculateSumVersions).reduce(Integer::sum).orElse(0);
        }

        public long calculateValue() {
            return switch (this.type) {
                case 0 -> this.subPackets.stream().map(Packet::calculateValue).reduce(Long::sum).orElseThrow();
                case 1 -> this.subPackets.stream().map(Packet::calculateValue).reduce((v1, v2) -> v1 * v2).orElseThrow();
                case 2 -> this.subPackets.stream().map(Packet::calculateValue).reduce(Long::min).orElseThrow();
                case 3 -> this.subPackets.stream().map(Packet::calculateValue).reduce(Long::max).orElseThrow();
                case 4 -> this.value;
                case 5 -> this.subPackets.get(0).calculateValue() > this.subPackets.get(1).calculateValue() ? 1 : 0;
                case 6 -> this.subPackets.get(0).calculateValue() < this.subPackets.get(1).calculateValue() ? 1 : 0;
                case 7 -> this.subPackets.get(0).calculateValue() == this.subPackets.get(1).calculateValue() ? 1 : 0;
                default -> throw new IllegalStateException("Unexpected type: " + this.type);
            };
        }
    }
}
