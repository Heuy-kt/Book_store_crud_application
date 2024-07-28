package com.prunnytest.bookstore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotAccessibleException extends RuntimeException{
    private final String message;
}
