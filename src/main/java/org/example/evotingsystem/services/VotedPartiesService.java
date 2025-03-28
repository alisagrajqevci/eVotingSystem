package org.example.evotingsystem.services;

import org.example.evotingsystem.models.Ballot;
import org.example.evotingsystem.models.Party;
import org.example.evotingsystem.repositories.BallotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VotedPartiesService {

    @Autowired
    private BallotRepository ballotRepository;

    public Map<String, Integer> getVoteCountsForParties() {
        List<Ballot> ballots = ballotRepository.findAll();
        Map<String, Integer> voteCounts = new HashMap<>();

        for (Ballot ballot : ballots) {
            for (Party party : ballot.getPartiesVotedFor()) {
                String partyName = party.getName();
                voteCounts.put(partyName, voteCounts.getOrDefault(partyName, 0) + 1);
            }
        }

        return voteCounts;
    }
}
