package edu.hw10;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;


public class RandomObjectGenerator {
    private static final int STRING_LENGTH = 20;
    private static final int NULL_PROBABILITY = 4;

    public <T> T nextObject(Class<T> inputClass, String factoryMethodName) {
        try {
            Method factoryMethod = inputClass.getMethod(factoryMethodName);
            Field[] fields = inputClass.getFields();
            T instance = (T) factoryMethod.invoke(null);
            for (Field field : fields) {
                boolean notNull = false;
                int minValue = Integer.MIN_VALUE;
                int maxValue = Integer.MAX_VALUE;
                notNull = field.isAnnotationPresent(NotNull.class);
                if (field.isAnnotationPresent(Min.class)) {
                    minValue = field.getAnnotation(Min.class).value();
                }
                if (field.isAnnotationPresent(Max.class)) {
                    maxValue = field.getAnnotation(Max.class).value();
                }
                setField(field, instance, minValue, maxValue, notNull);
            }
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T nextObject(Class<T> inputClass) {
        try {
            Constructor<T> constructor = inputClass.getDeclaredConstructor();
            Field[] fields = inputClass.getDeclaredFields();
            T instance = constructor.newInstance();

            for (Field field : fields) {
                boolean notNull = false;
                int minValue = Integer.MIN_VALUE;
                int maxValue = Integer.MAX_VALUE;
                notNull = field.isAnnotationPresent(NotNull.class);
                if (field.isAnnotationPresent(Min.class)) {
                    minValue = field.getAnnotation(Min.class).value();
                }
                if (field.isAnnotationPresent(Max.class)) {
                    maxValue = field.getAnnotation(Max.class).value();
                }
                setField(field, instance, minValue, maxValue, notNull);
            }
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void setField(Field field, T instance, int min, int max, boolean notNull) {
        Random random = new Random();
        try {
            if (notNull) {
                setFieldByType(field, instance, min, max);
            } else {
                int maxNull = NULL_PROBABILITY;
                int minNull = 0;
                int randomNum = random.nextInt((maxNull - minNull) + 1) + minNull;
                if (randomNum == 0) {
                    field.set(instance, null);
                } else {
                    setFieldByType(field, instance, min, max);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private <T> void setFieldByType(Field field, T instance, int min, int max) {
        try {
            Random random = new Random();
            byte[] array = new byte[STRING_LENGTH];
            String fieldName = field.getType().getSimpleName();
            ClassNames classNameEnum = ClassNames.valueOf(fieldName.toUpperCase());
            switch (classNameEnum) {
                case INT -> field.setInt(instance, random.nextInt((max - min) + 1) + min);
                case INTEGER -> field.set(instance, random.nextInt((max - min) + 1) + min);
                case LONG -> field.setLong(instance, random.nextLong((max - min) + 1) + min);
                case DOUBLE -> field.setDouble(instance, random.nextDouble((max - min) + 1) + min);
                case CHARACTER -> {
                    array = new byte[1];
                    new Random().nextBytes(array);
                    field.setChar(instance, new String(array).charAt(0));
                }
                case STRING -> {
                    new Random().nextBytes(array);
                    field.set(instance, new String(array));
                }
                case BOOLEAN -> field.setBoolean(instance, random.nextBoolean());
                default -> throw new RuntimeException();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    enum ClassNames {
        INTEGER(Integer.class.getSimpleName()),
        INT(int.class.getSimpleName()),
        LONG(Long.class.getSimpleName()),
        DOUBLE(Double.class.getSimpleName()),
        CHARACTER(Character.class.getSimpleName()),
        STRING(String.class.getSimpleName()),
        BOOLEAN(Boolean.class.getSimpleName());
        private String simpleName;

        ClassNames(String simpleName) {
            this.simpleName = simpleName;
        }
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NotNull {
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Min {
        int value();
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Max {
        int value();
    }
}
