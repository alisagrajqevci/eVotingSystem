package org.example.evotingsystem.services;

import org.example.evotingsystem.models.Election;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ElectionService {

    Election createElection(Election election);

    Optional<Election> getElectionById(long id);

    Election getElectionByName(String name);

    List<Election> getAllElections();

    List<Election> getElectionsByStartDateBefore(LocalDateTime startDate);

    List<Election> getOngoingElections(LocalDateTime currentDate);

    boolean hasOngoingElections();

    List<Election> getElectionsByEndDateBefore(LocalDateTime endDate);

    Election updateElection(long id, Election updatedElection);

    void deleteElection(long id);

    void saveElection(Election election);

    public List<Election> getElectionsByStatus(boolean status);

    Election findById(long id);
}

