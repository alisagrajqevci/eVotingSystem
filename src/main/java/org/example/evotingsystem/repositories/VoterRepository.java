package org.example.evotingsystem.repositories;

import org.example.evotingsystem.models.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {

    Optional<Voter> findByEmail(String email);

    Optional<Voter> findByPersonalNumber(String personalNumber);
}
