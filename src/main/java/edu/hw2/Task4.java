package edu.hw2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task4 {
    private Task4() {
    }

    private final static Logger LOGGER = LogManager.getLogger();

    public static CallingInfo callingInfo() {
        Throwable throwableObject = new Throwable();
        StackTraceElement[] stackTrace = throwableObject.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            LOGGER.info("Index " + i + " : " + stackTrace[i].toString());
        }
        int index = 1;
        if (stackTrace.length < 1) {
            index = 0;
        }
        return new CallingInfo(stackTrace[index].getClassName(), stackTrace[index].getMethodName());
    }

    public record CallingInfo(String className, String methodName) {
    }

}
