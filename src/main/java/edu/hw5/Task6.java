package edu.hw5;

import java.util.regex.Matcher;
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
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(mainString);
        return matcher.matches();
    }

}
