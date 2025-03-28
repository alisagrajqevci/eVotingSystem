package org.example.evotingsystem.repositories;

import org.example.evotingsystem.models.Ballot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BallotRepository extends JpaRepository<Ballot, Long> {

    List<Ballot> findByVoterId(long voterId);

    List<Ballot> findByCandidatesVotedForIdIn(List<Long> candidateIds);

    List<Ballot> findBySubmittedAtAfterAndSubmittedAtBefore(LocalDateTime startDate, LocalDateTime endDate);

    List<Ballot> findAll();
}
