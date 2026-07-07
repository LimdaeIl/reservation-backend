package com.reservation.backend.member.domain;

import com.reservation.backend.member.exception.MemberErrorCode;
import com.reservation.backend.member.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "v1_members")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberStatus status;

    private Member(String email, String nickname, String phone) {
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.role = MemberRole.USER;
        this.status = MemberStatus.ACTIVE;
    }

    public static Member create(String email, String nickname, String phone) {
        return new Member(email, nickname, phone);
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new MemberException(MemberErrorCode.REQUIRED_MEMBER_EMAIL);
        }
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new MemberException(MemberErrorCode.REQUIRED_MEMBER_NICKNAME);
        }

        if (nickname.length() < 2 || nickname.length() > 12) {
            throw new MemberException(MemberErrorCode.INVALID_NICKNAME_LENGTH);
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new MemberException(MemberErrorCode.REQUIRED_MEMBER_NICKNAME);
        }
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
