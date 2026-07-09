package com.reservation.backend.concert.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateConcertCategoryRequest(

        @NotBlank(message = "콘서트 카테고리 생성: 장르는 필수입니다.")
        @Size(max = 30, message = "콘서트 카테고리 생성: 장르는 30자 이하로 입력해주세요.")
        String genre,

        @NotBlank(message = "콘서트 카테고리 생성: 카테고리명은 필수입니다.")
        @Size(max = 50, message = "콘서트 카테고리 생성: 카테고리명은 50자 이하로 입력해주세요.")
        String name
) {

}
