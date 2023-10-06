package edu.hw1;

import java.util.Arrays;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {

    @ParameterizedTest
    @CsvSource(value = {
        "1,2,3,4; 0,6",
        "3,1; 4,0",
        "; ",
        "; 1,10",
    }, delimiterString = ";")
    void isNestableTest(
        @ConvertWith(IntArrayConverter.class) int[] firstArray,
        @ConvertWith(IntArrayConverter.class) int[] secondArray
    ) {
        // when
        boolean isNestable = Task3.isNestable(firstArray, secondArray);
        // then
        assertThat(isNestable).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "9,9,8; 8,9",
        "1,2,3,4; 2,3",
        "1; 1",
        "10,2; 5",
        "10,25; ",
    }, delimiterString = ";")
    void notNestableTest(
        @ConvertWith(IntArrayConverter.class) int[] firstArray,
        @ConvertWith(IntArrayConverter.class) int[] secondArray
    ) {
        // when
        boolean isNestable = Task3.isNestable(firstArray, secondArray);

        // then
        assertThat(isNestable).isFalse();
    }

    static class IntArrayConverter implements ArgumentConverter {

        @Override
        public Object convert(Object source, ParameterContext context)
            throws ArgumentConversionException {
            if (source == null) {
                return new int[] {};
            } else if (!(source instanceof String)) {
                throw new IllegalArgumentException(
                    "The argument must be a string: " + source);
            }
            try {
                return Arrays.stream(((String) source).split(",")).mapToInt(Integer::parseInt).toArray();
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to convert to int array", e);
            }
        }
    }
}
