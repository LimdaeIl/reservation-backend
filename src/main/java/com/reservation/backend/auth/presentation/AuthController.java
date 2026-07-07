package com.reservation.backend.auth.presentation;

import com.reservation.backend.auth.application.RefreshTokenCookieProvider;
import com.reservation.backend.auth.application.ReissueTokenService;
import com.reservation.backend.auth.application.SignInService;
import com.reservation.backend.auth.application.SignupService;
import com.reservation.backend.auth.presentation.request.SignInRequest;
import com.reservation.backend.auth.presentation.request.SignupRequest;
import com.reservation.backend.auth.presentation.response.ReissueResponse;
import com.reservation.backend.auth.presentation.response.ReissueTokenResult;
import com.reservation.backend.auth.presentation.response.SignInResponse;
import com.reservation.backend.auth.presentation.response.SignInResult;
import com.reservation.backend.auth.presentation.response.SignupResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final SignupService signupService;
    private final SignInService signInService;
    private final RefreshTokenCookieProvider cookieProvider;
    private final ReissueTokenService reissueTokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        SignupResponse response = signupService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @Valid @RequestBody SignInRequest request,
            HttpServletResponse servletResponse
    ) {
        SignInResult signInResult = signInService.signIn(request);

        cookieProvider.addRefreshTokenCookie(
                servletResponse,
                signInResult.refreshToken(),
                signInResult.refreshTokenRemainingSecond()
        );

        return ResponseEntity.ok(SignInResponse.of(
                        signInResult.memberId(),
                        signInResult.accessToken(),
                        signInResult.refreshToken()
                )
        );
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse servletResponse
    ) {
        ReissueTokenResult reissueTokenResult = reissueTokenService.reissue(refreshToken);

        cookieProvider.addRefreshTokenCookie(
                servletResponse,
                reissueTokenResult.newRefreshToken(),
                reissueTokenResult.refreshTokenRemainingSecond()
        );

        return ResponseEntity.ok(ReissueResponse.of(
                        reissueTokenResult.memberId(),
                        reissueTokenResult.newAccessToken(),
                        reissueTokenResult.newRefreshToken()
                )
        );
    }
}
