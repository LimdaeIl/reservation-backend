package com.reservation.backend.auth.presentation.request;

public record SignupRequest(

        String email,

        String password,

        String nickname,

        String phone
) {

}
