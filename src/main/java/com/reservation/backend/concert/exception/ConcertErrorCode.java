package com.reservation.backend.concert.exception;

import com.reservation.backend.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {

    CONCERT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트 카테고리: 존재하지 않는 카테고리입니다."),
    DUPLICATE_CONCERT_CATEGORY_GENRE(HttpStatus.CONFLICT, "콘서트 카테고리: 이미 존재하는 장르입니다."),
    DUPLICATE_CONCERT_CATEGORY_NAME(HttpStatus.CONFLICT, "콘서트 카테고리: 이미 존재하는 카테고리명입니다."),
    REQUIRED_CONCERT_CATEGORY_GENRE(HttpStatus.BAD_REQUEST, "콘서트 카테고리: 장르는 필수입니다."),
    REQUIRED_CONCERT_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "콘서트 카테고리: 카테고리명은 필수입니다."),
    INVALID_CONCERT_CATEGORY_GENRE_LENGTH(HttpStatus.BAD_REQUEST, "콘서트 카테고리: 장르는 30자 이하로 입력해주세요."),
    INVALID_CONCERT_CATEGORY_NAME_LENGTH(HttpStatus.BAD_REQUEST, "콘서트 카테고리: 카테고리명은 50자 이하로 입력해주세요.");

    private final HttpStatus httpStatus;
    private final String messageTemplate;

    @Override
    public HttpStatus status() {
        return httpStatus;
    }

    @Override
    public String message() {
        return messageTemplate;
    }
}