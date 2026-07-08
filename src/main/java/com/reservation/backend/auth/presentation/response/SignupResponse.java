package com.reservation.backend.auth.presentation.response;

import com.reservation.backend.member.domain.Member;

public record SignUpResponse(

) {

    public static SignUpResponse from(Member savedMember) {
        return null;
    }
}
