package edu.hw3;

import java.util.ArrayList;
import java.util.HashMap;

public class Task3 {
    private Task3() {
    }

    public static <T> HashMap<T, Integer> frequencyDict(ArrayList<T> inputList) {
        HashMap<T, Integer> resultMap = new HashMap<>();
        for (var item : inputList) {
            if (resultMap.containsKey(item)) {
                var newItem = resultMap.get(item) + 1;
                resultMap.put(item, newItem);
            } else {
                resultMap.put(item, 1);
            }
        }
        return resultMap;
    }
}
