package com.reservation.backend.auth.domain;

import com.reservation.backend.auth.exception.AuthErrorCode;
import com.reservation.backend.auth.exception.AuthException;
import com.reservation.backend.common.audit.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "v1_credentials")
@Entity
public class Credential extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")
    private Long id;

    @Column(name = "member_id", nullable = false, unique = true, updatable = false)
    private Long memberId;

    @Column(name = "password", nullable = false)
    private String password;

    private Credential(Long memberId, String password) {
        validateMemberId(memberId);
        validatePassword(password);

        this.memberId = memberId;
        this.password = password;
    }

    public static Credential create(Long memberId, String password) {
        return new Credential(memberId, password);
    }

    private void validateMemberId(Long memberId) {
        if (memberId == null) {
            throw new AuthException(AuthErrorCode.REQUIRED_MEMBER_ID);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new AuthException(AuthErrorCode.REQUIRED_PASSWORD);
        }
    }
}
