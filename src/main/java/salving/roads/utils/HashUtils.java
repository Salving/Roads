package salving.roads.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String Hash(String string, byte[] bytes ) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String username = "test";

            md.update(username.getBytes());
            md.update(bytes);
            byte[] hash = md.digest();

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) Math.abs(bytes[i]);
            }

            return new String(hash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return string;
        }
    }
}
