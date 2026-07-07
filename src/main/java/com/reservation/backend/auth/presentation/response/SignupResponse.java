package com.reservation.backend.auth.presentation.response;

import com.reservation.backend.member.domain.Member;

public record SignupResponse(

) {

    public static SignupResponse from(Member savedMember) {
        return null;
    }
}
