package com.reservation.backend.auth.application;

import com.reservation.backend.auth.domain.Credential;
import com.reservation.backend.auth.infrastructure.CredentialRepository;
import com.reservation.backend.auth.presentation.request.SignupRequest;
import com.reservation.backend.auth.presentation.response.SignupResponse;
import com.reservation.backend.member.domain.Member;
import com.reservation.backend.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SignupService {

    private final CredentialRepository credentialRepository;
    private final MemberRepository memberRepository;

    public SignupResponse signup(SignupRequest request) {
        validateDuplicateEmail(request.email());
        validateDuplicateNickname(request.nickname());
        validateDuplicatePhone(request.phone());

        Member member = Member.create(
                request.email(),
                request.nickname(),
                request.phone()
        );

        Member savedMember = memberRepository.save(member);

        Credential credential = Credential.create(
                member.getId(),
                request.password()
        );

        credentialRepository.save(credential);

        return SignupResponse.from(savedMember);
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
    }

    private void validateDuplicateNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
    }

    private void validateDuplicatePhone(String phone) {
        if (memberRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }
    }
}
