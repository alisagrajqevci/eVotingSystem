package org.example.evotingsystem.services;

import org.example.evotingsystem.models.Party;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface PartyService {

    Party createParty(Party party);

    Optional<Party> getPartyById(long id);

    Party getPartyByName(String name);

    Party getPartyByDescription(String description);

    List<Party> getAllParties();

    Party updateParty(long id, Party updatedParty);

    void deleteParty(long id);

    void saveParty(Party p);

    String getPartyNameById(long partyId);

    List<String> getAllPartyNames();

    Party findById(long id);
}
