package com.prunnytest.bookstore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlreadyExistsException extends RuntimeException{
    private final String message;

}
