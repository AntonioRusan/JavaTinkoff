package edu.hw1;

public final class Task1 {

    private static final int SECONDS_IN_MINUTE = 60;

    private Task1() {
    }

    public static int minutesToSeconds(String timeLength) {
        String[] timeSplit = timeLength.split(":");
        int result;
        if (timeSplit.length != 2) {
            result = -1;
        } else {
            int minutes = stringToNumber(timeSplit[0]);
            int seconds = stringToNumber(timeSplit[1]);
            if (minutes == -1 || seconds == -1) {
                result = -1;
            } else {
                result = (seconds < SECONDS_IN_MINUTE) ? (minutes * SECONDS_IN_MINUTE + seconds) : -1;
            }
        }
        return result;
    }

    private static int stringToNumber(String str) {
        int result = -1;
        if (str != null && str.matches("[0-9]+")) {
            result = Integer.parseInt(str);
        }
        return result;
    }
}
