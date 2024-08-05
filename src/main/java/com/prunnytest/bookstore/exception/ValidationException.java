package com.prunnytest.bookstore.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.EqualsAndHashCode;
import lombok.*;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends ConstraintViolationException {
    private final String msg;

    public ValidationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations, String msg) {
        super(message, constraintViolations);
        this.msg = msg;
    }
}
