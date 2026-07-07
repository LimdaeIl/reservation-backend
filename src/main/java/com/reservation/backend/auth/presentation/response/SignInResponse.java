package com.reservation.backend.auth.presentation.response;

public record SignInResponse(
        Long memberId,
        String accessToken,
        String refreshToken
) {

    public static SignInResponse of(Long memberId, String accessToken, String refreshToken) {
        return new SignInResponse(memberId, accessToken, refreshToken);
    }
}
