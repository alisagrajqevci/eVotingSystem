package org.example.evotingsystem.services;

import org.example.evotingsystem.models.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService {

    Candidate createCandidate(Candidate candidate);

    Optional<Candidate> getCandidateById(long id);

    List<Candidate> getCandidatesByFirstNameAndLastName(String firstName, String lastName);

    List<Candidate> getCandidatesByPartyId(long partyId);

    List<Candidate> getCandidatesByElectionId(long electionId);

    List<Candidate> getCandidatesByElectionIdAndPartyId(long electionId, long partyId);

    List<Candidate> getAllCandidates();

    Candidate updateCandidate(long id, Candidate updatedCandidate);

    void deleteCandidate(long id);

    void saveCandidate(Candidate candidate);


    List<Candidate> getCandidatesByPartyName(String partyName);

    List<Candidate> getCandidatesByParty(Long partyId);
    public boolean isNumberInPartyDuplicate(Long partyId, int numberInParty);

}
