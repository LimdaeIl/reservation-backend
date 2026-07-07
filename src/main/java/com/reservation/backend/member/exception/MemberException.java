package com.reservation.backend.member.exception;

import com.reservation.backend.common.exception.AppException;

public class MemberException extends AppException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException(MemberErrorCode errorCode, Object... parameters) {
        super(errorCode, parameters);
    }
}

