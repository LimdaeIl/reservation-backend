package com.reservation.backend.member.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank(message = "비밀번호 수정: 비밀번호는 필수입니다.")
        @Size(min = 8, max = 20, message = "비밀번호 수정: 비밀번호는 8자 이상 20자 이하입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\W_]{8,20}$",
                message = "비밀번호 수정: 비밀번호는 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        String beforePassword,
        @NotBlank(message = "비밀번호 수정: 비밀번호는 필수입니다.")
        @Size(min = 8, max = 20, message = "비밀번호 수정: 비밀번호는 8자 이상 20자 이하입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\W_]{8,20}$",
                message = "비밀번호 수정: 비밀번호는 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        String afterPassword
) {

}