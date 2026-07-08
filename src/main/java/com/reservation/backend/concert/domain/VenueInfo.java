package com.reservation.backend.concert.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VenueInfo {

    @Column(nullable = false)
    private String venueName; // 예: 올림픽공원 KSPO DOME

    @Column(nullable = false)
    private String address; // 예: 서울특별시 송파구 올림픽로 424

    private String detailAddress; // 예: KSPO DOME

    private Double latitude;

    private Double longitude;
}
