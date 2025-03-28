package org.example.evotingsystem.repositories;

import org.example.evotingsystem.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByFirstNameAndLastName(String firstName, String lastName);

    List<Candidate> findByPartyId(long partyId);

    List<Candidate> findByElectionId(long electionId);

    List<Candidate> findByElectionIdAndPartyId(long electionId, long partyId);

    List<Candidate> findAll();

    List<Candidate> findByPartyName(String partyName);

    boolean existsByPartyIdAndNumberInParty(Long partyId, int numberInParty);
}

