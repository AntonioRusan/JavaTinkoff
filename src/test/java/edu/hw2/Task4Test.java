package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.Task4.callingInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Простая проверка callingInfo")
    void checkCallingInfo() {
        CallingInfo tmp = callingInfo();
        assertThat(tmp.className()).isEqualTo("edu.hw2.Task4Test");
        assertThat(tmp.methodName()).isEqualTo("checkCallingInfo");
    }

    @Test
    @DisplayName("Проверка callingInfo, вызванного внутри вложенной функции")
    void checkCallingInfoDeep() {
        CallingInfo tmp = innerCallingInfo();
        assertThat(tmp.className()).isEqualTo("edu.hw2.Task4Test");
        assertThat(tmp.methodName()).isEqualTo("innerCallingInfo");
    }

    @Test
    @DisplayName("Проверка callingInfo, вызванного внутри вложенного класса")
    void checkCallingInfoInClass() {
        CallingInfo tmp = new TestClassCallingInfo().inClassCallingInfo();
        assertThat(tmp.className()).isEqualTo("edu.hw2.Task4Test$TestClassCallingInfo");
        assertThat(tmp.methodName()).isEqualTo("inClassCallingInfo");
    }

    private CallingInfo innerCallingInfo() {
        return callingInfo();
    }

    private static class TestClassCallingInfo {
        private CallingInfo inClassCallingInfo() {
            return callingInfo();
        }
    }

}
