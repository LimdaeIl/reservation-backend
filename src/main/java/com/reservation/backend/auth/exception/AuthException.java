package com.reservation.backend.auth.exception;


import com.reservation.backend.common.exception.AppException;

public class AuthException extends AppException {

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(AuthErrorCode errorCode, Object... parameters) {
        super(errorCode, parameters);
    }
}
