package com.reservation.backend.auth.infrastructure;

import com.reservation.backend.auth.domain.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

}
