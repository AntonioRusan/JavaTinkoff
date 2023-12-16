package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Generated
public class ByteBuddyTasksTest {
    @Test
    public void task1HelloTest() throws Exception {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        assertThat(dynamicType.getDeclaredConstructor().newInstance().toString()).isEqualTo("Hello, ByteBuddy!");
    }

    public static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }

    public static class MultiplicationInterceptor {
        public int sum(int a, int b) {
            return a * b;
        }
    }

    @Test
    public void task2InterceptorTest() throws Exception {
        ByteBuddyAgent.install();
        ArithmeticUtils utils = new ArithmeticUtils();
        new ByteBuddy()
            .redefine(MultiplicationInterceptor.class)
            .name(ArithmeticUtils.class.getName())
            .make()
            .load(
                ArithmeticUtils.class.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            )
            .getLoaded();
        assertThat(utils.sum(2, 3)).isEqualTo(6);
    }

}
