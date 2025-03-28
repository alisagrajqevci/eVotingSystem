package org.example.evotingsystem.services.impls;

import org.example.evotingsystem.models.Election;
import org.example.evotingsystem.repositories.ElectionRepository;
import org.example.evotingsystem.services.ElectionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionServiceImpl(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Override
    public Election createElection(Election election) {
        try {
            return electionRepository.save(election);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create election: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Election> getElectionById(long id) {
        try {
            return electionRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve election with ID: " + id, e);
        }
    }

    @Override
    public Election getElectionByName(String name) {
        try {
            return electionRepository.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve election with name: " + name, e);
        }
    }

    @Override
    public List<Election> getAllElections() {
        try {
            return electionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all elections: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Election> getElectionsByStartDateBefore(LocalDateTime startDate) {
        try {
            return electionRepository.findByStartDateBefore(startDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve elections starting before " + startDate + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Election> getOngoingElections(LocalDateTime currentDate) {
        try {
            return electionRepository.findByStartDateBeforeAndEndDateAfter(currentDate, currentDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ongoing elections: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean hasOngoingElections() {
        try {
            return !electionRepository.findByStartDateBeforeAndEndDateAfter(LocalDateTime.now(), LocalDateTime.now()).isEmpty();
        } catch (Exception e) {
            throw new RuntimeException("Failed to check ongoing elections: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Election> getElectionsByEndDateBefore(LocalDateTime endDate) {
        try {
            return electionRepository.findByEndDateBefore(endDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve elections ending before " + endDate + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Election updateElection(long id, Election updatedElection) {
        try {
            return electionRepository.findById(id)
                    .map(existingElection -> {
                        existingElection.setName(updatedElection.getName());
                        existingElection.setDescription(updatedElection.getDescription());
                        existingElection.setStartDate(updatedElection.getStartDate());
                        existingElection.setEndDate(updatedElection.getEndDate());
                        existingElection.setVoters(updatedElection.getVoters());
                        existingElection.setCandidates(updatedElection.getCandidates());
                        return electionRepository.save(existingElection);
                    })
                    .orElseThrow(() -> new RuntimeException("Election not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update election with ID: " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteElection(long id) {
        try {
            if (!electionRepository.existsById(id)) {
                throw new RuntimeException("Election not found with ID: " + id);
            }
            electionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete election with ID: " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void saveElection(Election election) {
        try {
            electionRepository.save(election);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save election: " + e.getMessage(), e);
        }

    }

    @Override
    public List<Election> getElectionsByStatus(boolean status) {
        try {
            return electionRepository.findByIsActive(status);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve elections by status: " + e.getMessage(), e);
        }
    }

    @Override
    public Election findById(long id) {
        try {
            return electionRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve election with ID: " + id, e);
        }
    }


}


