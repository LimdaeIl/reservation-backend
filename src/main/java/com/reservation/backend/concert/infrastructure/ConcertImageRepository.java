package com.reservation.backend.concert.infrastructure;

import com.reservation.backend.concert.domain.Concert;
import com.reservation.backend.concert.domain.ConcertImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertImageRepository extends JpaRepository<ConcertImage, Long> {

}
