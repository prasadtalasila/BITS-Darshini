package com.bits.protocolanalyzer.address;

public class MacAddress {

    private String macAddress;

    public MacAddress(String hexString) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            builder.append(hexString.substring(i, i + 2));
            builder.append(':');
        }
        builder.setLength(builder.length() - 1);
        this.macAddress = builder.toString();
    }

    @Override
    public String toString() {
        return this.macAddress;
    }
}
