package org.example.evotingsystem.services;

import org.example.evotingsystem.dto.BallotDTO;
import org.example.evotingsystem.models.Ballot;
import org.example.evotingsystem.models.Party;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BallotService {

    void createBallot(BallotDTO ballotDTO);

    Optional<Ballot> getBallotById(long id);

    List<Ballot> getBallotsByVoterId(long voterId);

    List<Ballot> getBallotsByCandidateIds(List<Long> candidateIds);

    List<Ballot> getBallotsSubmittedWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Ballot> getAllBallots();

    void deleteBallot(long id);


    List<Party> getVotedParties();
}