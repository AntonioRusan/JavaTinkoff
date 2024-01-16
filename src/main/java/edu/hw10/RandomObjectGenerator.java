package edu.hw10;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomObjectGenerator {
    private static final int STRING_LENGTH = 20;
    private static final int NULL_PROBABILITY = 4;

    private static final int MIN_VALUE = Integer.MIN_VALUE / 3;
    private static final int MAX_VALUE = Integer.MAX_VALUE / 3;

    public <T> T nextObject(Class<T> inputClass, String factoryMethodName) {
        try {
            List<Method> methods = Arrays.asList(inputClass.getDeclaredMethods());
            Method factoryMethod =
                methods.stream().filter(method -> method.getName().equals(factoryMethodName)).findFirst().orElse(null);
            Field[] fields = inputClass.getFields();
            List<T> fieldValueList = new ArrayList<>();
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
                fieldValueList.add((T) getFieldValue(field.getType(), minValue, maxValue, notNull));
            }
            T instance = (T) factoryMethod.invoke(null, fieldValueList.toArray());
            return instance;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T nextObject(Class<T> inputClass) {
        try {
            Field[] fields = inputClass.getDeclaredFields();

            Map<Class<T>, T> fieldToValue = new LinkedHashMap<>();
            for (Field field : fields) {
                boolean notNull = false;
                int minValue = MIN_VALUE;
                int maxValue = MAX_VALUE;
                notNull = field.isAnnotationPresent(NotNull.class);
                if (field.isAnnotationPresent(Min.class)) {
                    minValue = field.getAnnotation(Min.class).value();
                }
                if (field.isAnnotationPresent(Max.class)) {
                    maxValue = field.getAnnotation(Max.class).value();
                }
                fieldToValue.put(
                    (Class<T>) field.getType(),
                    (T) getFieldValue(field.getType(), minValue, maxValue, notNull)
                );
            }
            Class<?>[] fieldList = new Class<?>[fieldToValue.entrySet().size()];
            int ind = 0;
            for (var pair : fieldToValue.entrySet()) {
                fieldList[ind] = pair.getKey();
                ind++;
            }
            var constr = inputClass.getDeclaredConstructor(fieldList);
            T instance = constr.newInstance(fieldToValue.values().toArray());
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getFieldValue(Class<T> fieldType, int min, int max, boolean notNull) {
        Random random = new Random();
        if (notNull) {
            return getFieldValueByType(fieldType, min, max);
        } else {
            int maxNull = NULL_PROBABILITY;
            int minNull = 0;
            int randomNum = random.nextInt((maxNull - minNull) + 1) + minNull;
            if (randomNum == 0) {
                return null;
            } else {
                return getFieldValueByType(fieldType, min, max);
            }
        }

    }

    @SuppressWarnings("ReturnCount")
    private <T> T getFieldValueByType(Class<T> fieldType, int min, int max) {
        Random random = new Random();
        byte[] array = new byte[STRING_LENGTH];
        String fieldName = fieldType.getSimpleName();
        ClassNames classNameEnum = ClassNames.valueOf(fieldName.toUpperCase());
        switch (classNameEnum) {
            case INTEGER -> {
                Integer intValue = random.nextInt((max - min) + 1) + min;
                return (T) intValue;
            }
            case LONG -> {
                Long longValue = random.nextLong((max - min) + 1) + min;
                return (T) longValue;
            }
            case DOUBLE -> {
                Double doubleValue = random.nextDouble((max - min) + 1) + min;
                return (T) doubleValue;
            }
            case CHARACTER -> {
                array = new byte[1];
                new Random().nextBytes(array);
                Character charValue = new String(array).charAt(0);
                return (T) charValue;
            }
            case STRING -> {
                new Random().nextBytes(array);
                return (T) new String(array);
            }
            case BOOLEAN -> {
                Boolean boolValue = random.nextBoolean();
                return (T) boolValue;
            }
            default -> throw new RuntimeException();
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
