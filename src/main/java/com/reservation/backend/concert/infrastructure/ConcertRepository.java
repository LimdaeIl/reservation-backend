package com.reservation.backend.concert.infrastructure;

import com.reservation.backend.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

}
