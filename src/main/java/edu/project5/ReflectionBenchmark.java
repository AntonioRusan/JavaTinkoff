package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
@SuppressWarnings("UncommentedMain")
public class ReflectionBenchmark {
    private final static int WARMUP_SECONDS = 5;
    private final static int MEASUREMENT_SECONDS = 5;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(WARMUP_SECONDS))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(MEASUREMENT_SECONDS))
            .build();

        new Runner(options).run();
    }

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private MethodHandle lambdaMethodHandle;

    private StudentNameGetterFunction studentNameGetterFunction;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Ryan", "Gosling");
        String methodName = "name";

        method = Student.class.getMethod(methodName);
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        methodHandle = lookup.findVirtual(
            Student.class,
            methodName,
            MethodType.methodType(String.class)
        );

        CallSite site = LambdaMetafactory.metafactory(
            lookup,
            "apply",
            MethodType.methodType(StudentNameGetterFunction.class),
            MethodType.methodType(String.class, Student.class),
            methodHandle,
            methodHandle.type()
        );
        lambdaMethodHandle = site.getTarget();

        studentNameGetterFunction = (StudentNameGetterFunction) lambdaMethodHandle.invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflectionAccess(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandleAccess(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invokeExact(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMethodHandleAccess(Blackhole bh) throws Throwable {
        String name = studentNameGetterFunction.apply(student);
        bh.consume(name);
    }

    record Student(String name, String surname) {
    }

    @FunctionalInterface
    interface StudentNameGetterFunction {
        String apply(Student student);
    }
}
