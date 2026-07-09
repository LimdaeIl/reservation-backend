package com.reservation.backend.concert.infrastructure;

import com.reservation.backend.concert.domain.ConcertCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertCategoryRepository extends JpaRepository<ConcertCategory, Long> {

    Optional<ConcertCategory> findByGenre(String genre);

    boolean existsByGenre(String genre);
}
