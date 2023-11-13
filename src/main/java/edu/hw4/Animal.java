package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    @SuppressWarnings("MagicNumber")
    public int paws() {
        return switch (type) {
            case CAT, DOG -> 4;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> 8;
        };
    }

    public Set<ValidationError> validateAnimal() {
        Set<ValidationError> errors = new HashSet<>();

        if (name == null || name.trim().isEmpty()) {
            errors.add(new ValidationError("name", ValidationError.ValidationErrorType.EMPTY_VALUE));
        }

        if (type == null) {
            errors.add(new ValidationError("type", ValidationError.ValidationErrorType.EMPTY_VALUE));
        }

        if (sex == null) {
            errors.add(new ValidationError("sex", ValidationError.ValidationErrorType.EMPTY_VALUE));
        }

        if (age <= 0) {
            errors.add(new ValidationError("age", ValidationError.ValidationErrorType.INVALID_VALUE));
        }

        if (height <= 0) {
            errors.add(new ValidationError("height", ValidationError.ValidationErrorType.INVALID_VALUE));
        }

        if (weight <= 0) {
            errors.add(new ValidationError("weight", ValidationError.ValidationErrorType.INVALID_VALUE));
        }

        return errors;
    }

}
