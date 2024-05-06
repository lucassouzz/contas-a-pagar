package com.br.totvs.core.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static com.br.totvs.core.constants.Constants.Password.ALGORITHM;
import static com.br.totvs.core.constants.Constants.Password.SECRET;

public class PasswordUtil {

    public static String encrypt(String value) throws Exception {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

        byte[] encryptedBytes = cipher.doFinal(value.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String value) throws Exception {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

        byte[] encryptedBytes = Base64.getDecoder().decode(value);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }

    private static Cipher getCipher(int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM.getValue());
        SecretKey secretKey = new SecretKeySpec(SECRET.getValue().getBytes(), ALGORITHM.getValue());
        cipher.init(mode, secretKey);

        return cipher;
    }
}
