package edu.hw5;

import java.util.regex.Pattern;

public class Task6 {
    private Task6() {

    }

    public static boolean isSubsequence(String subString, String mainString) {
        StringBuilder regexStrBuilder = new StringBuilder();
        for (int i = 0; i < subString.length(); i++) {
            regexStrBuilder.append(".*").append(Pattern.quote(String.valueOf(subString.charAt(i))));
        }
        regexStrBuilder.append(".*");
        String regexStr = regexStrBuilder.toString();
        return Pattern.compile(regexStr).matcher(mainString).find();
    }

}
