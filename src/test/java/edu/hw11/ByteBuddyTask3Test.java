package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ByteBuddyTask3Test {
    @Test
    public void task3() throws Exception {
        /*Class<?> loadedClass = new ByteBuddy()
            .subclass(Object.class)
            .name("Fibonacci")
            .defineMethod("fib", int.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameter(int.class, "n")
            .intercept(new Implementation.Simple(new FibonacciByteCodeAppender()))
            .make()
            .load(ByteBuddyTask3Test.class.getClassLoader())
            .getLoaded();

        Method[] methods = loadedClass.getDeclaredMethods();
        Method fibMethod = loadedClass.getMethod("fib", int.class);
        int result = (int) fibMethod.invoke(null, 10);
        System.out.println(result);*/
    }

    public static class FibonacciByteCodeAppender implements ByteCodeAppender {
        @Override
        public Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription instrumentedMethod) {
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            //methodVisitor.visitJumpInsn(Opcodes.IFLE, new Label());

            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 2);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 3);

            Label loopStart = new Label();
            Label loopEnd = new Label();

            methodVisitor.visitLabel(loopStart);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 1);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3);
            methodVisitor.visitInsn(Opcodes.IADD);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 2);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 3);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitJumpInsn(Opcodes.IFLE, loopEnd);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, loopStart);

            methodVisitor.visitLabel(loopEnd);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
            methodVisitor.visitInsn(Opcodes.IRETURN);

            methodVisitor.visitMaxs(2, 4);
            methodVisitor.visitEnd();
            return new Size(2, 4);
        }
    }
}
