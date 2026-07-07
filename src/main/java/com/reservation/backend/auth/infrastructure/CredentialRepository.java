package com.reservation.backend.auth.infrastructure;

import com.reservation.backend.auth.domain.Credential;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findByMemberId(Long memberId);
}
