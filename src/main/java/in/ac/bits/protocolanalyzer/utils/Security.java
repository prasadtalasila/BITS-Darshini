package in.ac.bits.protocolanalyzer.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

    public static final String SHA256_ALGORITHM = "SHA-256";

    public static String createHash(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA256_ALGORITHM);
            md.update(str.getBytes("UTF-8"));
            byte[] digest = md.digest();
            String hash = String.format("%064x",
                    new java.math.BigInteger(1, digest));
            return hash;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
