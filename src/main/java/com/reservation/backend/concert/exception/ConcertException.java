package com.reservation.backend.concert.exception;

import com.reservation.backend.common.exception.AppException;
import com.reservation.backend.common.exception.ErrorCode;

public class ConcertException extends AppException  {

    public ConcertException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ConcertException(ErrorCode errorCode, Object... parameters) {
        super(errorCode, parameters);
    }
}
