package com.reservation.backend.auth.application;

import com.reservation.backend.auth.exception.AuthErrorCode;
import com.reservation.backend.auth.exception.AuthException;
import com.reservation.backend.auth.infrastructure.RefreshTokenRepository;
import com.reservation.backend.auth.infrastructure.jwt.JWTHashUtil;
import com.reservation.backend.auth.infrastructure.jwt.JwtProperties;
import com.reservation.backend.auth.infrastructure.jwt.JwtProvider;
import com.reservation.backend.auth.presentation.response.ReissueTokenResult;
import com.reservation.backend.member.domain.Member;
import com.reservation.backend.member.infrastructure.MemberRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReissueTokenService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTHashUtil jwtHashUtil;
    private final JwtProvider jwtProvider;

    public ReissueTokenResult reissue(String refreshToken) {
        validateRefreshToken(refreshToken);

        Long memberId = jwtProvider.getMemberIdFromRefreshToken(refreshToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_FOUND_BY_MEMBER_ID));

        String newAccessToken = jwtProvider.createAccessToken(
                member.getId(),
                member.getRole().name()
        );
        String newRefreshToken = jwtProvider.createRefreshToken(member.getId());

        String hashedOldRefreshToken = jwtHashUtil.sha256(refreshToken);
        String hashedNewRefreshToken = jwtHashUtil.sha256(newRefreshToken);

        Duration refreshTokenTtl =
                Duration.ofMillis(jwtProvider.getRefreshTokenExpirationMillis());

        refreshTokenRepository.rotateIfMatches(
                member.getId(),
                hashedOldRefreshToken,
                hashedNewRefreshToken,
                refreshTokenTtl
        );

        return ReissueTokenResult.of(
                member.getId(),
                newAccessToken,
                newRefreshToken,
                refreshTokenTtl.toSeconds()
        );
    }

    private void validateRefreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AuthException(AuthErrorCode.MISSING_REFRESH_TOKEN);
        }
    }
}
