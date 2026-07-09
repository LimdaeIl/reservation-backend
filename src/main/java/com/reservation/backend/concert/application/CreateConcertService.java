package com.reservation.backend.concert.application;

import com.reservation.backend.concert.domain.Concert;
import com.reservation.backend.concert.domain.ConcertCategory;
import com.reservation.backend.concert.domain.VenueInfo;
import com.reservation.backend.concert.exception.ConcertErrorCode;
import com.reservation.backend.concert.exception.ConcertException;
import com.reservation.backend.concert.infrastructure.ConcertCategoryRepository;
import com.reservation.backend.concert.infrastructure.ConcertRepository;
import com.reservation.backend.concert.presentation.request.CreateConcertRequest;
import com.reservation.backend.concert.presentation.response.CreateConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CreateConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertCategoryRepository categoryRepository;

    public CreateConcertResponse create(CreateConcertRequest request) {
        ConcertCategory category = categoryRepository.findById(request.concertCategoryId())
                .orElseThrow(() -> new ConcertException(ConcertErrorCode.CONCERT_CATEGORY_NOT_FOUND));

        validateRepresentativeImage(request);

        VenueInfo venueInfo = VenueInfo.create(
                request.venueName(),
                request.address(),
                request.detailAddress(),
                request.latitude(),
                request.longitude()
        );

        Concert concert = Concert.create(
                request.title(),
                request.description(),
                venueInfo,
                category,
                request.startDateTime(),
                request.endDateTime()
        );

        request.images().forEach(imageRequest ->
                concert.addImage(
                        imageRequest.imageUrl(),
                        imageRequest.sortOrder(),
                        imageRequest.representative()
                )
        );

        Concert savedConcert = concertRepository.save(concert);

        return CreateConcertResponse.from(savedConcert);
    }

    private void validateRepresentativeImage(CreateConcertRequest request) {
        long representativeCount = request.images().stream()
                .filter(CreateConcertRequest.ConcertImageRequest::representative)
                .count();

        if (representativeCount != 1) {
            throw new ConcertException(ConcertErrorCode.INVALID_REPRESENTATIVE_IMAGE);
        }
    }
}
