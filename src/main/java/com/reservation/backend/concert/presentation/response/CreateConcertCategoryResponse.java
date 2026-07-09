package com.reservation.backend.concert.presentation.response;

import com.reservation.backend.concert.domain.ConcertCategory;
import java.time.Instant;

public record CreateConcertCategoryResponse(
        Long concertCategoryId,
        String genre,
        String name,
        boolean active,
        Long createdBy,
        Instant createdAt,
        Long updatedBy,
        Instant updatedAt
) {

    public static CreateConcertCategoryResponse from(ConcertCategory concertCategory) {
        return new CreateConcertCategoryResponse(
                concertCategory.getId(),
                concertCategory.getGenre(),
                concertCategory.getName(),
                concertCategory.isActive(),
                concertCategory.getCreatedBy(),
                concertCategory.getCreatedAt(),
                concertCategory.getUpdatedBy(),
                concertCategory.getUpdatedAt()
        );
    }
}
