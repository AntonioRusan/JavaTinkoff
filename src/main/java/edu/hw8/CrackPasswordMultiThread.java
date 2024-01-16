package edu.hw8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CrackPasswordMultiThread {
    public Map<String, String> hashToUser = new ConcurrentHashMap<>();
    public final List<String> allPasswords;
    public final static String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    public final static int NUM_SYMBOLS = SYMBOLS.length();
    public final static char MAX_SYMBOL = SYMBOLS.charAt(NUM_SYMBOLS - 1);
    public final static char MIN_SYMBOL = SYMBOLS.charAt(0);
    public final int maxPasswordLength;
    public final long numOfPasswords;
    public final int numOfThreads;

    public CrackPasswordMultiThread(int maxPasswordLength, int numOfThreads) {
        this.maxPasswordLength = maxPasswordLength;
        this.numOfThreads = numOfThreads;
        this.numOfPasswords = calculateNumOfPasswords(maxPasswordLength);
        this.allPasswords = crackPassword();
    }

    public long calculateNumOfPasswords(int maxPasswordLength) {
        long sum = 0;
        for (int i = 1; i <= maxPasswordLength; i++) {
            sum += (long) Math.pow(NUM_SYMBOLS, i);
        }
        return sum;
    }

    public void fillMap(List<String> inputStrings) {
        for (var line : inputStrings) {
            String[] array = line.split(" ");
            if (array.length == 2) {
                hashToUser.put(array[1], array[0]);
            }
        }
    }

    public void crackPasswordMultiThread() {
        try (ExecutorService executor = Executors.newFixedThreadPool(numOfThreads)) {
            List<Future> tasks = new ArrayList<>();
            long batchSize = numOfPasswords / numOfThreads;
            for (int i = 0; i < numOfThreads; i++) {
                long start;
                long end;
                if (i != numOfThreads - 1) {
                    start = i * batchSize;
                    end = (i + 1) * batchSize - 1;
                } else {
                    start = i * batchSize;
                    end = numOfPasswords - 1;
                }
                tasks.add(executor.submit(() -> checkBatchHash(start, end)));
            }
            for (var future : tasks) {
                try {
                    future.get();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            executor.shutdown();
        }
    }

    public void checkBatchHash(long start, long end) {
        for (int i = (int) start; i <= end; i++) {
            checkHash(allPasswords.get(i));
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

    public List<String> crackPassword() {
        List<String> combinations = new ArrayList<>();
        char[] charArrayMaxPassword = new char[maxPasswordLength];
        Arrays.fill(charArrayMaxPassword, MAX_SYMBOL);
        String maxPassword = new String(charArrayMaxPassword);
        String currentPassword = String.valueOf(MIN_SYMBOL);
        while (!currentPassword.equals(maxPassword)) {
            combinations.add(currentPassword);
            currentPassword = nextPassword(currentPassword);
        }
        combinations.add(currentPassword);
        return combinations;
    }
}
