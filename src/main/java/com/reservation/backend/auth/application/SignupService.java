package com.reservation.backend.auth.application;

import com.reservation.backend.auth.domain.Credential;
import com.reservation.backend.auth.exception.AuthErrorCode;
import com.reservation.backend.auth.exception.AuthException;
import com.reservation.backend.auth.infrastructure.CredentialRepository;
import com.reservation.backend.auth.presentation.request.SignUpRequest;
import com.reservation.backend.auth.presentation.response.SignUpResponse;
import com.reservation.backend.member.domain.Member;
import com.reservation.backend.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SignupService {

    private final CredentialRepository credentialRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest request) {
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
                passwordEncoder.encode(request.password())
        );

        credentialRepository.save(credential);

        return SignUpResponse.from(savedMember);
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new AuthException(AuthErrorCode.DUPLICATE_EMAIL);
        }
    }

    private void validateDuplicateNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new AuthException(AuthErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void validateDuplicatePhone(String phone) {
        if (memberRepository.existsByPhone(phone)) {
            throw new AuthException(AuthErrorCode.DUPLICATE_PHONE);
        }
    }
}
