package com.reservation.backend.auth.presentation.response;

public record SignInResult(
        Long memberId,
        String accessToken,
        String refreshToken,
        long refreshTokenRemainingSecond
) {

    public static SignInResult of(Long memberId, String accessToken, String refreshToken,
            long refreshTokenRemainingSecond) {
        return new SignInResult(memberId, accessToken, refreshToken,
                refreshTokenRemainingSecond);
    }
}
