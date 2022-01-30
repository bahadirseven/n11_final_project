package com.n11.graduation.cs.exception;

import com.n11.graduation.cs.constant.ErrorMessage;

public class CsNoSuchAlgorithmException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CsNoSuchAlgorithmException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
