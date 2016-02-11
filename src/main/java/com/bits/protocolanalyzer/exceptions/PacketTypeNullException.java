package com.bits.protocolanalyzer.exceptions;

/**
 * 
 * @author crygnus
 *
 */
public class PacketTypeNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PacketTypeNullException(String message) {
        super(message);
    }
}
