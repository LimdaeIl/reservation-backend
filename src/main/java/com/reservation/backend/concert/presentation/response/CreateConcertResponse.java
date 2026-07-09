package com.reservation.backend.concert.presentation.response;

import com.reservation.backend.concert.domain.Concert;
import com.reservation.backend.concert.domain.ConcertImage;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record CreateConcertResponse(
        Long concertId,
        Long concertCategoryId,
        String concertCategoryGenre,
        String concertCategoryName,
        String title,
        String description,
        String venueName,
        String address,
        String detailAddress,
        Double latitude,
        Double longitude,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String status,
        List<ConcertImageResponse> images,
        Long createdBy,
        Instant createdAt,
        Long updatedBy,
        Instant updatedAt
) {

    public static CreateConcertResponse from(Concert concert) {
        return new CreateConcertResponse(
                concert.getId(),
                concert.getCategory().getId(),
                concert.getCategory().getGenre(),
                concert.getCategory().getName(),
                concert.getTitle(),
                concert.getDescription(),
                concert.getVenueInfo().getVenueName(),
                concert.getVenueInfo().getAddress(),
                concert.getVenueInfo().getDetailAddress(),
                concert.getVenueInfo().getLatitude(),
                concert.getVenueInfo().getLongitude(),
                concert.getStartDateTime(),
                concert.getEndDateTime(),
                concert.getStatus().name(),
                concert.getImages().stream()
                        .map(ConcertImageResponse::from)
                        .toList(),
                concert.getCreatedBy(),
                concert.getCreatedAt(),
                concert.getUpdatedBy(),
                concert.getUpdatedAt()
        );
    }

    public record ConcertImageResponse(
            Long imageId,
            String imageUrl,
            int sortOrder,
            boolean representative
    ) {

        public static ConcertImageResponse from(ConcertImage image) {
            return new ConcertImageResponse(
                    image.getId(),
                    image.getImageUrl(),
                    image.getSortOrder(),
                    image.isRepresentative()
            );
        }
    }
}
