package reddist.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class CryptoHash {
    public static String getHash(String textToHash) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance( "SHA-256" );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Change this to UTF-16 if needed
        md.update( textToHash.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();

        return String.format( "%064x", new BigInteger( 1, digest ) );
    }
}