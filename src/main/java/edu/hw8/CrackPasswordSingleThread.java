package edu.hw8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrackPasswordSingleThread {
    public Map<String, String> hashToUser = new HashMap<>();
    public final static String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    public final static int NUM_SYMBOLS = SYMBOLS.length();
    public final static char MAX_SYMBOL = SYMBOLS.charAt(NUM_SYMBOLS - 1);
    public final static char MIN_SYMBOL = SYMBOLS.charAt(0);

    public final int maxPasswordLength;

    public CrackPasswordSingleThread(int maxPasswordLength) {
        this.maxPasswordLength = maxPasswordLength;
    }

    public void fillMap(List<String> inputStrings) {
        for (var line : inputStrings) {
            String[] array = line.split(" ");
            if (array.length == 2) {
                hashToUser.put(array[1], array[0]);
            }
        }
    }

    public void checkHash(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        String hashKey = sb.toString();
        if (hashToUser.containsKey(hashKey)) {
            String user = hashToUser.get(hashKey);
            hashToUser.remove(hashKey);
            hashToUser.put(password, user);
        }
    }

    public String nextPassword(String password) {
        char[] charString = password.toCharArray();
        boolean needResize = true;
        for (int i = charString.length - 1; i >= 0; i--) {
            if (charString[i] != MAX_SYMBOL) {
                charString[i] = SYMBOLS.charAt(SYMBOLS.indexOf(charString[i]) + 1);
                int j = i + 1;
                while (j < charString.length) {
                    charString[j] = MIN_SYMBOL;
                    j++;
                }
                needResize = false;
                break;
            }
        }
        if (needResize && charString.length < maxPasswordLength) {
            char[] extendedCharString = new char[charString.length + 1];
            Arrays.fill(extendedCharString, '0');
            return new String(extendedCharString);
        }
        return new String(charString);
    }

    public void crackPassword() {
        char[] charArrayMaxPassword = new char[maxPasswordLength];
        Arrays.fill(charArrayMaxPassword, MAX_SYMBOL);
        String maxPassword = new String(charArrayMaxPassword);
        String currentPassword = String.valueOf(MIN_SYMBOL);
        while (!currentPassword.equals(maxPassword)) {
            checkHash(currentPassword);
            currentPassword = nextPassword(currentPassword);
        }
    }

}
