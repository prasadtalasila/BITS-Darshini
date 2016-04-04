package in.ac.bits.protocolanalyzer.utils;

public class ByteOperator {

    public static int parseBytesint(byte[] bytes)
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

    public static byte parseBytesbyte(byte[] bytes)
            throws ArrayIndexOutOfBoundsException {
        if (bytes.length > 1) {
            throw new ArrayIndexOutOfBoundsException(
                    "Byte array length exceeds 4!");
        } else {
            int val = 0;
            for (int i = 0; i < bytes.length; i++) {
                val = val << 8;
                val = val | bytes[i] & 0xFF;
            }
            return (byte) val;
        }
    }

    public static short parseBytesshort(byte[] bytes)
            throws ArrayIndexOutOfBoundsException {
        if (bytes.length > 2) {
            throw new ArrayIndexOutOfBoundsException(
                    "Byte array length exceeds 4!");
        } else {
            int val = 0;
            for (int i = 0; i < bytes.length; i++) {
                val = val << 8;
                val = val | bytes[i] & 0xFF;
            }
            return (short) val;
        }
    }

    public static long parseByteslong(byte[] bytes) {
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
