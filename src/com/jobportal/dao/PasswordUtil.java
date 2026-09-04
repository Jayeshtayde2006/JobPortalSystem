package com.jobportal.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    public static String hashPassword(String password) {

        try {
            byte[] salt = new byte[SALT_LENGTH];

            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            byte[] hash = factory.generateSecret(spec)
                    .getEncoded();

            return ITERATIONS + ":"
                    + Base64.getEncoder().encodeToString(salt)
                    + ":"
                    + Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException |
                 InvalidKeySpecException e) {

            throw new RuntimeException(
                    "Password hashing failed!",
                    e
            );
        }
    }

    public static boolean verifyPassword(
            String password,
            String storedPassword) {

        try {

            String[] parts = storedPassword.split(":");

            if (parts.length != 3) {
                return false;
            }

            int iterations = Integer.parseInt(parts[0]);

            byte[] salt =
                    Base64.getDecoder().decode(parts[1]);

            byte[] storedHash =
                    Base64.getDecoder().decode(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    iterations,
                    KEY_LENGTH
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            byte[] calculatedHash =
                    factory.generateSecret(spec)
                            .getEncoded();

            if (calculatedHash.length != storedHash.length) {
                return false;
            }

            int result = 0;

            for (int i = 0; i < calculatedHash.length; i++) {
                result |= calculatedHash[i] ^ storedHash[i];
            }

            return result == 0;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isHashed(String password) {

        if (password == null) {
            return false;
        }

        String[] parts = password.split(":");

        return parts.length == 3;
    }
}