package in.ac.bits.protocolanalyzer.utils;

public class ByteOperator {

    public static int parseBytes(byte[] bytes)
            throws ArrayIndexOutOfBoundsException {
        if (bytes.length > 4) {
            throw new ArrayIndexOutOfBoundsException(
                    "Byte array length exceeds 4!");
        } else {
            int val = 0;
            for (int i = 0; i < bytes.length; i++) {
                val = val << 8;
                val = val | bytes[i] & 0xFF;
            }
            return val;
        }
    }

    public static long parseBytesLong(byte[] bytes) {
        if (bytes.length > 8) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            long val = 0l;
            for (int i = 0; i < bytes.length; i++) {
                val = val << 8;
                val = val | bytes[i] & 0xFF;
            }
            return val;
        }
    }

}
