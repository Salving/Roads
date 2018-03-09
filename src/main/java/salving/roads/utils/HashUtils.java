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

            for (int i = 0; i < hash.length; i++) {
                hash[i] = (byte) Math.abs(hash[i]);
                if (hash[i] == "/n".getBytes(StandardCharsets.UTF_8)[0]) {
                    hash[i] = 70;
                }
                if (hash[i] <= 20) {
                    hash[i] = (byte) (hash[i] + 21);
                }
            }

            return new String(hash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return string;
        }
    }
}
