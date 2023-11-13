package edu.hw4;

public record ValidationError(
    String fieldName,
    ValidationErrorType errorType) {

    enum ValidationErrorType {
        INVALID_VALUE, EMPTY_VALUE
    }

}
