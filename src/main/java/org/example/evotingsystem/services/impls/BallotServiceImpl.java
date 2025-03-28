package org.example.evotingsystem.services.impls;

import org.example.evotingsystem.dto.BallotDTO;
import org.example.evotingsystem.models.Ballot;
import org.example.evotingsystem.models.Candidate;
import org.example.evotingsystem.models.Party;
import org.example.evotingsystem.models.Voter;
import org.example.evotingsystem.repositories.*;
import org.example.evotingsystem.services.BallotService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BallotServiceImpl implements BallotService {

    private final BallotRepository ballotRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final PartyRepository partyRepository;
    private final ElectionRepository electionRepository;

    public BallotServiceImpl(BallotRepository ballotRepository, VoterRepository voterRepository, CandidateRepository candidateRepository, PartyRepository partyRepository, ElectionRepository electionRepository) {
        this.ballotRepository = ballotRepository;
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.partyRepository = partyRepository;
        this.electionRepository = electionRepository;
    }

    @Override
    public void createBallot(BallotDTO ballotDTO) {
        Voter voter = voterRepository.findById(ballotDTO.getVoterId())
                .orElseThrow(() -> new RuntimeException("Voter not found"));

        if (voter.getHasVoted()) {
            throw new RuntimeException("Voter has already voted");
        }


        Party party = partyRepository.findById(ballotDTO.getPartyId())
                .orElseThrow(() -> new RuntimeException("Party not found"));

        List<Candidate> candidates = ballotDTO.getCandidateIds().stream()
                .map(candidateId -> candidateRepository.findById(candidateId)
                        .orElseThrow(() -> new RuntimeException("Candidate not found")))
                .collect(Collectors.toList());

        Ballot ballot = new Ballot();
        ballot.setVoter(voter);
        ballot.setParty(party);
        ballot.setPartiesVotedFor(List.of(party));
        ballot.setCandidatesVotedFor(candidates);
        ballot.setSubmittedAt(LocalDateTime.now());

        ballotRepository.save(ballot);

        voter.setHasVoted(true);
        voterRepository.save(voter);
    }

    @Override
    public Optional<Ballot> getBallotById(long id) {
        try {
            return ballotRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ballot with ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Ballot> getBallotsByVoterId(long voterId) {
        try {
            return ballotRepository.findByVoterId(voterId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ballots for voter with ID " + voterId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Ballot> getBallotsByCandidateIds(List<Long> candidateIds) {
        try {
            return ballotRepository.findByCandidatesVotedForIdIn(candidateIds);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ballots for candidates with IDs " + candidateIds + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Ballot> getBallotsSubmittedWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return ballotRepository.findBySubmittedAtAfterAndSubmittedAtBefore(startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ballots submitted between " + startDate + " and " + endDate + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Ballot> getAllBallots() {
        try {
            return ballotRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all ballots: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBallot(long id) {
        try {
            if (ballotRepository.existsById(id)) {
                ballotRepository.deleteById(id);
            } else {
                throw new RuntimeException("Ballot not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete ballot with ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Party> getVotedParties() {
        try {
            return ballotRepository.findAll()
                    .stream()
                    .map(Ballot::getPartiesVotedFor)
                    .flatMap(List::stream)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve voted parties: " + e.getMessage(), e);
        }
    }
}