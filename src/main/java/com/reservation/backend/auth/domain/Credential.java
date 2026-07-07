package com.reservation.backend.auth.domain;

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
public class Credential {

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
            throw new IllegalArgumentException("인증/인가: 회원 ID는 필수입니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호: 비밀번호는 필수입니다.");
        }
    }
}
