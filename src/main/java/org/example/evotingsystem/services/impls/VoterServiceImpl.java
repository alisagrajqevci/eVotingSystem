package org.example.evotingsystem.services.impls;

import org.example.evotingsystem.models.Voter;
import org.example.evotingsystem.repositories.VoterRepository;
import org.example.evotingsystem.services.VoterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoterServiceImpl implements VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Override
    public Optional<Voter> getVoterById(Long id) {
        return voterRepository.findById(id);
    }

    @Override
    public Voter getVoterByPersonalNumberAndBirthdate(String personalNumber, LocalDate birthdate) {
        return voterRepository.findAll()
                .stream()
                .filter(voter -> voter.getPersonalNumber().equals(personalNumber) && voter.getBirthdate().equals(birthdate))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Voter getByHasVoted(boolean hasVoted) {
        return voterRepository.findAll()
                .stream()
                .filter(voter -> voter.getHasVoted() == hasVoted)
                .findFirst()
                .orElse(null);
    }



    @Override
    public void markVoterAsVoted(Long voterId) {
        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new RuntimeException("Voter not found with ID: " + voterId));
        voter.setHasVoted(true);
        voterRepository.save(voter);
    }

    @Override
    @Transactional
    public void markVoterAsLoggedIn(Long voterId) {
        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new RuntimeException("Voter not found"));
        voter.setIsLoggedIn(true);
        voterRepository.save(voter);

    }

    @Override
    public void submitVote(Long voterId, Long partyId, List<Long> candidateIds) {
        Optional<Voter> voterOptional = voterRepository.findById(voterId);
        if (voterOptional.isPresent()) {
            Voter voter = voterOptional.get();
            voter.setHasVoted(true);
            voterRepository.save(voter);
        }

        markVoterAsVoted(voterId);
    }

    @Override
    public boolean hasVoted(Long voterId) {
        Optional<Voter> voterOptional = voterRepository.findById(voterId);
        return voterOptional.map(Voter::getHasVoted).orElse(false);
    }

    @Override
    public boolean isVoterLoggedIn(Long voterId) {
        Optional<Voter> voterOptional = voterRepository.findById(voterId);
        return voterOptional.map(Voter::getIsLoggedIn).orElse(false);
    }


    @Override
    public Voter saveVoter(Voter voter) {
        return voterRepository.save(voter);
    }
}
