package com.giwiro.vertigo.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Crypto {
    public static String hashPassword(String plainPassword) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(plainPassword, salt);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        boolean verified;

        if (null == hashedPassword || !hashedPassword.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        verified = BCrypt.checkpw(plainPassword, hashedPassword);

        return verified;
    }

}
