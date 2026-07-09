package com.reservation.backend.concert.presentation;

import com.reservation.backend.auth.infrastructure.security.AuthenticatedMember;
import com.reservation.backend.concert.application.CreateConcertService;
import com.reservation.backend.concert.presentation.request.CreateConcertCategoryRequest;
import com.reservation.backend.concert.presentation.request.CreateConcertRequest;
import com.reservation.backend.concert.presentation.response.CreateConcertCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
@RestController
public class ConcertController {

    private final CreateConcertService createConcertService;

    @PostMapping
    public ResponseEntity<CreateConcertCategoryResponse> create(
            @AuthenticationPrincipal AuthenticatedMember member,
            @Valid @RequestBody CreateConcertRequest request
    ) {
        CreateConcertCategoryResponse response = createConcertService.create(request);

        return ResponseEntity.ok(response);

    }

}
