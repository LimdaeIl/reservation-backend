package com.reservation.backend.auth.presentation.response;

import java.time.Duration;

public record ReissueTokenResult(
        Long memberId,
        String newAccessToken,
        String newRefreshToken,
        long refreshTokenRemainingSecond
) {

    public static ReissueTokenResult of(Long memberId, String newAccessToken,
            String newRefreshToken, long refreshTokenRemainingSecond) {
        return new ReissueTokenResult(memberId, newAccessToken, newRefreshToken, refreshTokenRemainingSecond);
    }
}
