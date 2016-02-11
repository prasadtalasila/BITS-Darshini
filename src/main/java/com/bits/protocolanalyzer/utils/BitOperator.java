package com.bits.protocolanalyzer.utils;

/**
 * 
 * @author crygnus
 *
 */

public class BitOperator {

    /**
     * Returns the bit in byte b at the given index. Note that index must be
     * between 0-7
     * 
     * @param b
     * @param index
     * @return bit 0 or 1
     * @throws ArrayIndexOutOfBoundsException
     */
    public static int getBit(byte b, int index)
            throws ArrayIndexOutOfBoundsException {

        if (index < 0 || index > 7) {
            throw new ArrayIndexOutOfBoundsException(
                    "Provided bit location is beyond the range 0-7");
        }
        return (b >> index) & 1;
    }

    /**
     * Returns int array 'bit' of size 8 with bit[i] = bit at index 'i' in byte
     * b
     * 
     * @param b
     * @return array of int
     */
    public static int[] getBits(byte b) {
        int[] bits = new int[8];
        for (int i = 0; i < 8; i++) {
            bits[i] = getBit(b, i);
        }
        return bits;
    }

    /**
     * Returns int equivalent of bit array of specified start location and length
     * @param b
     * @param startBit
     * @param numberOfBits
     * @return int val
     * @throws ArrayIndexOutOfBoundsException
     */
    public static int getValue(byte b, int startBit, int numberOfBits)
            throws ArrayIndexOutOfBoundsException {

        if (startBit + numberOfBits > 8) {
            throw new ArrayIndexOutOfBoundsException(
                    "Relevant bits are beyond this byte!");
        } else {
            int val = 0;
            for (int i = 0; i < numberOfBits; i++) {
                val = val | (getBit(b, i) << i + startBit);
            }
            return val;
        }
    }

    /**
     * Returns integer equivalent of the nibble in byte b with given nibble
     * index (either 0 or 1)
     * 
     * @param b
     * @param nibbleIndex
     * @return int nibble
     * @throws ArrayIndexOutOfBoundsException
     */
    public static int getNibble(byte b, int nibbleIndex)
            throws ArrayIndexOutOfBoundsException {
        if (nibbleIndex == 0) {
            return b & 0xF;
        } else if (nibbleIndex == 1) {
            return (b >> 4) & 0xF;
        } else {
            throw new ArrayIndexOutOfBoundsException(
                    "Nibble index can only be 0 or 1");
        }
    }
}
