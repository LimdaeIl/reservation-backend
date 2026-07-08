package com.reservation.backend.member.presentation.response;

import com.reservation.backend.member.domain.Member;

public record GetMeResponse(
        Long memberId,
        String email,
        String nickname,
        String phone,
        String role,
        String status
) {

    public static GetMeResponse of(Member member) {
        return new GetMeResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getPhone(),
                member.getRole().name(),
                member.getStatus().name()
        );
    }
}
