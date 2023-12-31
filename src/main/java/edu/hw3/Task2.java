package edu.hw3;

import java.util.ArrayList;
import java.util.Stack;

public class Task2 {
    private Task2() {
    }

    private static final String ERROR_NOT_BALANCED = "Bracket sequence is not balanced!";

    public static ArrayList<String> clusterizeBrackets(String inputString) throws IllegalArgumentException {
        ArrayList<String> resultClusters = new ArrayList<>();
        Stack<Character> bracketStack = new Stack<>();
        int prevInd = 0;
        for (int i = 0; i < inputString.length(); i++) {
            char current = inputString.charAt(i);
            if (current == '(') {
                bracketStack.push(current);
            } else if (current == ')') {
                if (bracketStack.isEmpty()) {
                    throw new IllegalArgumentException(ERROR_NOT_BALANCED);
                }
                bracketStack.pop();
                if (bracketStack.isEmpty()) {
                    resultClusters.add(inputString.substring(prevInd, i + 1));
                    prevInd = i + 1;
                }
            }
        }
        if (!bracketStack.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NOT_BALANCED);
        }
        return resultClusters;
    }
}
