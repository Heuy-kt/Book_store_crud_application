package com.prunnytest.bookstore.exception;

import java.util.Map;

public record CustomErrorResponse(
        Map<String, String> errors
) {}
