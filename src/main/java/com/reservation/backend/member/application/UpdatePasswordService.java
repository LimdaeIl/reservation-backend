package com.reservation.backend.member.application;

import com.reservation.backend.auth.application.SignOutService;
import com.reservation.backend.auth.domain.Credential;
import com.reservation.backend.auth.exception.AuthErrorCode;
import com.reservation.backend.auth.exception.AuthException;
import com.reservation.backend.auth.infrastructure.CredentialRepository;
import com.reservation.backend.auth.infrastructure.security.AuthenticatedMember;
import com.reservation.backend.member.presentation.request.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UpdatePasswordService {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignOutService signOutService;

    public void updatePassword(AuthenticatedMember member, UpdatePasswordRequest request) {
        Credential credential = credentialRepository.findByMemberId(member.memberId())
                .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_FOUND_CREDENTIAL));

        validateCurrentPassword(request.beforePassword(), credential.getPassword());
        validateDifferentPassword(request.beforePassword(), request.afterPassword());

        String encodedPassword = passwordEncoder.encode(request.afterPassword());
        credential.updatePassword(encodedPassword);

        signOutService.signOutByMemberId(member.memberId());
    }

    private void validateCurrentPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new AuthException(AuthErrorCode.INVALID_CURRENT_PASSWORD);
        }
    }

    private void validateDifferentPassword(String beforePassword, String afterPassword) {
        if (beforePassword.equals(afterPassword)) {
            throw new AuthException(AuthErrorCode.NEW_PASSWORD_SAME_AS_CURRENT);
        }
    }
}