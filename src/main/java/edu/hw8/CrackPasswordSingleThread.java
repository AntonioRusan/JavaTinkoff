package edu.hw8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrackPasswordSingleThread {
    public static Map<String, String> hashToUser = new HashMap<>();
    public static String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static int NUM_SYMBOLS = SYMBOLS.length();
    public static char MAX_SYMBOL = SYMBOLS.charAt(NUM_SYMBOLS - 1);
    public static char MIN_SYMBOL = SYMBOLS.charAt(0);

    public static int MAX_PASSWORD_LENGTH = 3;

    public void fillMap(List<String> inputStrings)
    {
        for (var line: inputStrings)
        {
            String [] array = line.split(" ");
            if (array.length == 2)
            {
                hashToUser.put(array[1], array[0]);
            }
        }
    }

    public void checkHash(String password)
    {
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

    public static String nextPassword(String password)
    {
        char[] charString = password.toCharArray();
        boolean needResize = true;
        for (int i = charString.length - 1; i >= 0; i--)
        {
            if (charString[i] != MAX_SYMBOL)
            {
                charString[i] = SYMBOLS.charAt(SYMBOLS.indexOf(charString[i]) + 1);
                int j = i + 1;
                while (j < charString.length)
                {
                    charString[j] = MIN_SYMBOL;
                    j++;
                }
                needResize = false;
                break;
            }
        }
        if (needResize && charString.length < MAX_PASSWORD_LENGTH) {
            char [] extendedCharString = new char[charString.length + 1];
            Arrays.fill(extendedCharString, '0');
            return new String(extendedCharString);
        }
        return new String(charString);
    }

    public static void crackPassword()
    {
        char [] charArrayMaxPassword = new char[MAX_PASSWORD_LENGTH];
        Arrays.fill(charArrayMaxPassword, MAX_SYMBOL);
        String maxPassword = new String(charArrayMaxPassword);
        String currentPassword = String.valueOf(MIN_SYMBOL);
        System.out.println(maxPassword);
        List<String> combs = new ArrayList<>();
        while (!currentPassword.equals(maxPassword))
        {
            combs.add(currentPassword);
            //System.out.println(currentPassword);
            currentPassword = nextPassword(currentPassword);
        }
        combs.add(currentPassword);
        System.out.println(combs.size());
    }

}
