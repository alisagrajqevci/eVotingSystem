package org.example.evotingsystem.services.impls;

import org.example.evotingsystem.models.Party;
import org.example.evotingsystem.repositories.PartyRepository;
import org.example.evotingsystem.services.PartyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;

    public PartyServiceImpl(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    @Override
    public Party createParty(Party party) {
        try {
            return partyRepository.save(party);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create party: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Party> getPartyById(long id) {
        try {
            return partyRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve party with ID: " + id, e);
        }
    }

    @Override
    public Party getPartyByName(String name) {
        try {
            return partyRepository.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve party with name: " + name, e);
        }
    }

    @Override
    public Party getPartyByDescription(String description) {
        return null;
    }

    @Override
    public List<Party> getAllParties() {
        try {
            return partyRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all parties: " + e.getMessage(), e);
        }
    }

    @Override
    public Party updateParty(long id, Party updatedParty) {
        try {
            return partyRepository.findById(id)
                    .map(existingParty -> {
                        existingParty.setName(updatedParty.getName());
                        existingParty.setCandidates(updatedParty.getCandidates());
                        return partyRepository.save(existingParty);
                    })
                    .orElseThrow(() -> new RuntimeException("Party not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update party with ID: " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteParty(long id) {
        try {
            if (!partyRepository.existsById(id)) {
                throw new RuntimeException("Party not found with ID: " + id);
            }
            partyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete party with ID: " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void saveParty(Party p) {
        try {
            partyRepository.save(p);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save party: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPartyNameById(long partyId) {
        try {
            return partyRepository.findById(partyId)
                    .map(Party::getName)
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve party name for party ID " + partyId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getAllPartyNames() {
        try {
            return partyRepository.findAll()
                    .stream()
                    .map(Party::getName)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all party names: " + e.getMessage(), e);
        }
    }

    @Override
    public Party findById(long id) {
        try {
            return partyRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve party with ID " + id + ": " + e.getMessage(), e);
        }
    }

}

