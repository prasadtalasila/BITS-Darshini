package com.bits.protocolanalyzer.analyzer.network;

public class IPv4Address {

    private String[] address = new String[4];

    public IPv4Address(byte[] address) {
        /*
         * later create a check on length and if other than 4 then throw an
         * exception
         */
        for (int i = 0; i < this.address.length; i++) {
            // to convert to an unsigned byte, operate bitwise & with 0xFF
            this.address[i] = String.valueOf(address[i] & 0xFF);
        }
    }

    @Override
    public String toString() {
        String addressString = address[0] + "." + address[1] + "." + address[2]
                + "." + address[3];
        return addressString;
    }

}
