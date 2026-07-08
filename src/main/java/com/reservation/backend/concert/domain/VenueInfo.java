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

    @Column(name = "venue_name", nullable = false, length = 100)
    private String venueName; // 예: 올림픽공원 KSPO DOME

    @Column(name = "address", nullable = false, length = 200)
    private String address; // 예: 서울특별시 송파구 올림픽로 424

    @Column(name = "detail_address", length = 100)
    private String detailAddress; // 예: KSPO DOME

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
