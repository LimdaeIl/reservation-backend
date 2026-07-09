package com.reservation.backend.concert.presentation.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public record CreateConcertRequest(
        @NotNull(message = "콘서트 생성: 콘서트 카테고리는 필수입니다.")
        Long concertCategoryId,

        @NotBlank(message = "콘서트 생성: 제목은 필수입니다.")
        @Size(max = 100, message = "콘서트 생성: 제목은 100자 이하입니다.")
        String title,

        @NotBlank(message = "콘서트 생성: 설명은 필수입니다.")
        String description,

        @NotBlank(message = "콘서트 생성: 공연장 이름은 필수입니다.")
        @Size(max = 100, message = "콘서트 생성: 공연장 이름은 100자 이하입니다.")
        String venueName,

        @NotBlank(message = "콘서트 생성: 주소는 필수입니다.")
        @Size(max = 200, message = "콘서트 생성: 주소는 200자 이하입니다.")
        String address,

        @Size(max = 100, message = "콘서트 생성: 상세 주소는 100자 이하입니다.")
        String detailAddress,

        @NotNull(message = "콘서트 생성: 위도는 필수입니다.")
        @DecimalMin(value = "-90.0", message = "콘서트 생성: 위도는 -90 이상이어야 합니다.")
        @DecimalMax(value = "90.0", message = "콘서트 생성: 위도는 90 이하이어야 합니다.")
        Double latitude,

        @NotNull(message = "콘서트 생성: 경도는 필수입니다.")
        @DecimalMin(value = "-180.0", message = "콘서트 생성: 경도는 -180 이상이어야 합니다.")
        @DecimalMax(value = "180.0", message = "콘서트 생성: 경도는 180 이하이어야 합니다.")
        Double longitude,

        @NotNull(message = "콘서트 생성: 시작 일시는 필수입니다.")
        LocalDateTime startDateTime,

        @NotNull(message = "콘서트 생성: 종료 일시는 필수입니다.")
        LocalDateTime endDateTime,

        @NotEmpty(message = "콘서트 생성: 이미지는 1개 이상 필요합니다.")
        List<@Valid ConcertImageRequest> images
) {

    public record ConcertImageRequest(
            @NotBlank(message = "콘서트 생성: 이미지 URL은 필수입니다.")
            @Size(max = 512, message = "콘서트 생성: 이미지 URL은 512자 이하입니다.")
            String imageUrl,

            @Min(value = 0, message = "콘서트 생성: 이미지 순서는 0 이상이어야 합니다.")
            int sortOrder,

            boolean representative
    ) {

    }
}