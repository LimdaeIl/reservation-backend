package com.reservation.backend.member.presentation;

import com.reservation.backend.auth.infrastructure.security.AuthenticatedMember;
import com.reservation.backend.member.application.GetMeService;
import com.reservation.backend.member.presentation.response.GetMeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final GetMeService getMeService;

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<GetMeResponse> getMe(
            @AuthenticationPrincipal AuthenticatedMember member
    ) {
        GetMeResponse response = getMeService.getMe(member);

        return ResponseEntity.ok(response);
    }

    // 비밀번호 변경

    // 이메일 변경

    // 휴대전화번호 변경

    // 닉네임 변경
}
