package edu.hw3;

import java.util.ArrayList;
import java.util.Stack;

public class Task2 {
    private Task2() {
    }

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
                    throw new IllegalArgumentException("Bracket sequence is not balanced!");
                }
                bracketStack.pop();
                if (bracketStack.isEmpty()) {
                    resultClusters.add(inputString.substring(prevInd, i + 1));
                    prevInd = i + 1;
                }
            }
        }
        return resultClusters;
    }
}
