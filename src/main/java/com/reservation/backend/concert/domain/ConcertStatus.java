package com.reservation.backend.concert.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConcertStatus {

    PREPARING("준비중"),
    OPEN("공개"),
    CLOSED("종료"),
    CANCELED("취소");

    private final String description;
}
