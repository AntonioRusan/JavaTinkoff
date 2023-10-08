package edu.hw1;

public final class Task5 {
    private Task5() {
    }

    public static boolean isPalindromeDescendant(Integer inputNumber) {
        String inputStr = inputNumber.toString();
        StringBuilder inputStrBuilder = new StringBuilder(inputStr);
        boolean result = isPalindrome(inputStrBuilder);
        while (inputStrBuilder.length() >= 2 && !result) {
            if (isPalindrome(inputStrBuilder)) {
                result = true;
            } else {
                StringBuilder sumStrBuilder = new StringBuilder();
                for (int i = 0; i < inputStrBuilder.length() - 1; i += 2) {
                    sumStrBuilder.append((inputStrBuilder.charAt(i) - '0') + (inputStrBuilder.charAt(i + 1) - '0'));
                }
                if (inputStrBuilder.length() % 2 != 0) {
                    sumStrBuilder.append((inputStrBuilder.charAt(inputStrBuilder.length() - 1)));
                }
                inputStrBuilder = sumStrBuilder;
            }
        }
        return result;
    }

    private static boolean isPalindrome(StringBuilder inputString) {
        int i = 0;
        int j = inputString.length() - 1;
        boolean result = true;
        while (i < j && result) {
            if (inputString.charAt(i) == inputString.charAt(j)) {
                i++;
                j--;
            } else {
                result = false;
            }
        }
        return result;
    }
}
