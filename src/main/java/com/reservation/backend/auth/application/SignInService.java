package com.reservation.backend.auth.application;

import com.reservation.backend.auth.domain.Credential;
import com.reservation.backend.auth.exception.AuthErrorCode;
import com.reservation.backend.auth.exception.AuthException;
import com.reservation.backend.auth.infrastructure.CredentialRepository;
import com.reservation.backend.auth.infrastructure.RefreshTokenRepository;
import com.reservation.backend.auth.infrastructure.jwt.JWTHashUtil;
import com.reservation.backend.auth.infrastructure.jwt.JwtProvider;
import com.reservation.backend.auth.presentation.request.SignInRequest;
import com.reservation.backend.auth.presentation.response.SignInResult;
import com.reservation.backend.member.domain.Member;
import com.reservation.backend.member.infrastructure.MemberRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SignInService {

    private final MemberRepository memberRepository;
    private final CredentialRepository credentialRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTHashUtil jwtHashUtil;
    private final JwtProvider jwtProvider;

    public SignInResult signIn(SignInRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_SIGN_IN));

        validateActiveMember(member);

        Credential credential = credentialRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_FOUND_CREDENTIAL));

        validatePassword(request, credential);

        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());

        saveRefreshToken(member.getId(), refreshToken);

        return SignInResult.of(
                member.getId(),
                accessToken,
                refreshToken,
                jwtProvider.getRefreshTokenRemainingSeconds(refreshToken)
        );
    }

    private void saveRefreshToken(Long memberId, String refreshToken) {
        String hashedRefreshToken = jwtHashUtil.sha256(refreshToken);
        long refreshTokenRemainingMillis = jwtProvider.getRefreshTokenRemainingMillis(refreshToken);

        refreshTokenRepository.save(
                memberId,
                hashedRefreshToken,
                Duration.ofMillis(refreshTokenRemainingMillis)
        );
    }

    private void validatePassword(SignInRequest request, Credential credential) {
        if (!passwordEncoder.matches(request.password(), credential.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_SIGN_IN);
        }
    }


    private void validateActiveMember(Member member) {
        if (!member.isActive()) {
            throw new AuthException(AuthErrorCode.INACTIVE_MEMBER);
        }
    }
}
