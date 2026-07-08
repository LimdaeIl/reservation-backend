package com.reservation.backend.member.application;

import com.reservation.backend.auth.infrastructure.security.AuthenticatedMember;
import com.reservation.backend.member.domain.Member;
import com.reservation.backend.member.exception.MemberErrorCode;
import com.reservation.backend.member.exception.MemberException;
import com.reservation.backend.member.infrastructure.MemberRepository;
import com.reservation.backend.member.presentation.response.GetMeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetMeService {

    private final MemberRepository memberRepository;

    public GetMeResponse getMe(AuthenticatedMember member) {
        Member findMember = memberRepository.findById(member.memberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        validateMemberStatus(findMember);

        return GetMeResponse.of(findMember);
    }

    private void validateMemberStatus(Member findMember) {
        if (!findMember.isActive()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_ACTIVE);
        }
    }


}
