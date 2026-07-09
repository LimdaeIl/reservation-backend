package com.reservation.backend.concert.presentation;

import com.reservation.backend.auth.infrastructure.security.AuthenticatedMember;
import com.reservation.backend.concert.application.CreateConcertCategoryService;
import com.reservation.backend.concert.presentation.request.CreateConcertCategoryRequest;
import com.reservation.backend.concert.presentation.response.CreateConcertCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/concert-categories")
@RestController
public class ConcertCategoryController {

    private final CreateConcertCategoryService createConcertCategoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CreateConcertCategoryResponse> create(
            @RequestBody CreateConcertCategoryRequest request
    ) {
        CreateConcertCategoryResponse response = createConcertCategoryService.create(request);

        return ResponseEntity.ok(response);
    }

    // 콘서트 카테고리 조회
    // 콘서트 카테고리 수정
    // 콘서트 카테고리 삭제

}
