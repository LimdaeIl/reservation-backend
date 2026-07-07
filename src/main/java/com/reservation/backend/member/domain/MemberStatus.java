package com.reservation.backend.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    DELETED("삭제");

    private final String description;
}
