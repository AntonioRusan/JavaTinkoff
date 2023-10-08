package edu.hw1;

public final class Task4 {
    private Task4() {
    }

    public static String fixString(String inputStr) {
        if (inputStr == null) {
            return "";
        } else {
            StringBuilder inputStrBuilder = new StringBuilder(inputStr);
            for (int i = 0; i < inputStr.length() - 1; i += 2) {
                inputStrBuilder.setCharAt(i, inputStr.charAt(i + 1));
                inputStrBuilder.setCharAt(i + 1, inputStr.charAt(i));
            }
            return inputStrBuilder.toString();
        }
    }

}
