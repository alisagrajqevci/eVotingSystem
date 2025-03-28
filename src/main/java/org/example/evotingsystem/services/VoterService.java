package org.example.evotingsystem.services;

import org.example.evotingsystem.models.Voter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoterService {

    Optional<Voter> getVoterById(Long id);

    Voter getVoterByPersonalNumberAndBirthdate(String personalNumber, LocalDate birthdate);

    Voter getByHasVoted(boolean hasVoted);
    public void markVoterAsVoted(Long voterId);

    public void markVoterAsLoggedIn(Long voterId);
    public void submitVote(Long voterId, Long partyId, List<Long> candidateIds);
    boolean hasVoted(Long voterId);

    public boolean isVoterLoggedIn(Long voterId);

    Voter saveVoter(Voter voter);
}