package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ByteBuddyTasksTest {
    @Test
    @Generated
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
    @Generated
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

    //Аннотация, чтобы исключить генерируемые классы из jaccoco и не было ошибок на github
    @Documented
    @Retention(RUNTIME)
    @Target({TYPE, METHOD, CONSTRUCTOR})
    public @interface Generated {
    }
}
