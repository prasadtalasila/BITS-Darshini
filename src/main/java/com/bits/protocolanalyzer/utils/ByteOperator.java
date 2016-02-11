package com.bits.protocolanalyzer.utils;

public class ByteOperator {

    public static int parseBytes(byte[] bytes)
            throws ArrayIndexOutOfBoundsException {
        if (bytes.length > 4) {
            throw new ArrayIndexOutOfBoundsException(
                    "Byte array length exceeds 4!");
        } else {
            int val = 0;
            for (int i = bytes.length - 1; i >= 0; i--) {
                val = val << 8;
                val = val | bytes[i] & 0xFF;
            }
            return val;
        }
    }

}
