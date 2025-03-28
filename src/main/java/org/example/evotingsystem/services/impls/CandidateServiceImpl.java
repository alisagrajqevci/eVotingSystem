package org.example.evotingsystem.services.impls;

import org.example.evotingsystem.models.Candidate;
import org.example.evotingsystem.repositories.CandidateRepository;
import org.example.evotingsystem.services.CandidateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate createCandidate(Candidate candidate) {
        try {
            return candidateRepository.save(candidate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create candidate: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Candidate> getCandidateById(long id) {
        try {
            return candidateRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve candidate with ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getCandidatesByFirstNameAndLastName(String firstName, String lastName) {
        try {
            return candidateRepository.findByFirstNameAndLastName(firstName, lastName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve candidates with name " + firstName + " " + lastName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getCandidatesByPartyId(long partyId) {
        try {
            return candidateRepository.findByPartyId(partyId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve candidates for party ID " + partyId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getCandidatesByElectionId(long electionId) {
        try {
            return candidateRepository.findByElectionId(electionId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve candidates for election ID " + electionId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getCandidatesByElectionIdAndPartyId(long electionId, long partyId) {
        try {
            return candidateRepository.findByElectionIdAndPartyId(electionId, partyId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve candidates for election ID " + electionId + " and party ID " + partyId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getAllCandidates() {
        try {
            return candidateRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all candidates: " + e.getMessage(), e);
        }
    }

    @Override
    public Candidate updateCandidate(long id, Candidate updatedCandidate) {
        try {
            return candidateRepository.findById(id)
                    .map(existingCandidate -> {
                        existingCandidate.setFirstName(updatedCandidate.getFirstName());
                        existingCandidate.setLastName(updatedCandidate.getLastName());
                        existingCandidate.setParty(updatedCandidate.getParty());
                        existingCandidate.setElection(updatedCandidate.getElection());
                        existingCandidate.setModifiedAt(updatedCandidate.getModifiedAt());
                        existingCandidate.setModifiedBy(updatedCandidate.getModifiedBy());
                        return candidateRepository.save(existingCandidate);
                    })
                    .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update candidate with ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCandidate(long id) {
        try {
            if (candidateRepository.existsById(id)) {
                candidateRepository.deleteById(id);
            } else {
                throw new RuntimeException("Candidate not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete candidate with ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        try {
            candidateRepository.save(candidate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save candidate: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getCandidatesByPartyName(String partyName) {
        try {
            return candidateRepository.findByPartyName(partyName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve candidates for party name " + partyName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getCandidatesByParty(Long partyId) {
        try {
            return candidateRepository.findByPartyId(partyId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve candidates for party ID " + partyId + ": " + e.getMessage(), e);
        }
    }

    public boolean isNumberInPartyDuplicate(Long partyId, int numberInParty) {
        return candidateRepository.existsByPartyIdAndNumberInParty(partyId, numberInParty);
    }

}

