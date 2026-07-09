package com.reservation.backend.concert.application;

import com.reservation.backend.concert.domain.ConcertCategory;
import com.reservation.backend.concert.exception.ConcertErrorCode;
import com.reservation.backend.concert.exception.ConcertException;
import com.reservation.backend.concert.infrastructure.ConcertCategoryRepository;
import com.reservation.backend.concert.presentation.request.CreateConcertCategoryRequest;
import com.reservation.backend.concert.presentation.response.CreateConcertCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CreateConcertCategoryService {

    private final ConcertCategoryRepository concertCategoryRepository;

    public CreateConcertCategoryResponse create(CreateConcertCategoryRequest request) {
        validateDuplicateGenre(request.genre());

        ConcertCategory concertCategory = ConcertCategory.create(
                request.genre(),
                request.name()
        );

        ConcertCategory savedCategory = concertCategoryRepository.save(concertCategory);

        return CreateConcertCategoryResponse.from(savedCategory);
    }

    private void validateDuplicateGenre(String genre) {
        if (concertCategoryRepository.existsByGenre(genre)) {
            throw new ConcertException(ConcertErrorCode.DUPLICATE_CONCERT_CATEGORY_GENRE);
        }
    }
}