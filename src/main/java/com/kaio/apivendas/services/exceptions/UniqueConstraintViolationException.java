package com.kaio.apivendas.services.exceptions;

import java.io.Serial;

public class UniqueConstraintViolationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID =1L;

    public UniqueConstraintViolationException(String msg) {
        super(msg);
    }

    public UniqueConstraintViolationException(String msg, Throwable cause) {
        super(msg, cause);
    }


}
