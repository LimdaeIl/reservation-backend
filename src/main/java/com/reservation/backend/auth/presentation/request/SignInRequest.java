package com.reservation.backend.auth.presentation.request;

public record SignInRequest(
        String email,
        String password
) {

}
