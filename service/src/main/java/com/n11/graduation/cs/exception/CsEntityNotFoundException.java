package com.n11.graduation.cs.exception;

import com.n11.graduation.cs.constant.ErrorMessage;

public class CsEntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CsEntityNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
