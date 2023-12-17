package edu.hw11;

import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.junit.jupiter.api.Test;

public class ByteBuddyTask3Test {
    @Test
    public void task3() throws Exception {
        Class<?> loadedClass = new ByteBuddy()
            .subclass(Object.class)
            .name("Fibonacci")
            .defineMethod("fib", int.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameter(int.class, "n")
            .intercept(new Implementation.Simple(new FibonacciByteCodeAppender()))
            .make()
            .load(ByteBuddyTask3Test.class.getClassLoader())
            .getLoaded();

        Method fibMethod = loadedClass.getDeclaredMethod("fib", int.class);
        int result = (int) fibMethod.invoke(null, 10);
        System.out.println(result);
    }

    public static class FibonacciByteCodeAppender implements ByteCodeAppender {
        @Override
        public Size apply(
            MethodVisitor methodVisitor,
            Implementation.Context context,
            MethodDescription instrumentedMethod
        ) {
            methodVisitor.visitCode();

            Label start = new Label();
            Label loopCheck = new Label();
            Label loopBody = new Label();
            Label end = new Label();

            methodVisitor.visitLabel(start);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitJumpInsn(Opcodes.IFLE, end);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 2);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 3);

            methodVisitor.visitLabel(loopCheck);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitJumpInsn(Opcodes.IFLE, end);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3);
            methodVisitor.visitInsn(Opcodes.IADD);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 4);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 2);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 4);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, 3);

            methodVisitor.visitJumpInsn(Opcodes.GOTO, loopBody);

            methodVisitor.visitLabel(loopBody);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, loopCheck);

            methodVisitor.visitLabel(end);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3);
            methodVisitor.visitInsn(Opcodes.IRETURN);

            methodVisitor.visitMaxs(2, 5);
            methodVisitor.visitEnd();
            return new Size(2, 5);
        }
    }
}
