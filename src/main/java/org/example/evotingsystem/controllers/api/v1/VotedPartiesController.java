package org.example.evotingsystem.controllers.api.v1;

import org.example.evotingsystem.services.VotedPartiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/voted-parties")
public class VotedPartiesController {

    @Autowired
    private VotedPartiesService votedPartiesService;

    @GetMapping
    public Map<String, Integer> getVotedParties() {
        return votedPartiesService.getVoteCountsForParties();
    }
}
