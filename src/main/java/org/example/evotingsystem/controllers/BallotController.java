package org.example.evotingsystem.controllers;

import org.example.evotingsystem.services.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ballot")
public class BallotController {

    @Autowired
    private VoterService voterService;

    @PostMapping
    public String submitVote(@RequestParam Long voterId, @RequestParam Long partyId, @RequestParam List<Long> candidateIds) {
        boolean isLoggedIn = voterService.isVoterLoggedIn(voterId);
        if (!isLoggedIn) {
            return "redirect:/login";
        }

        boolean hasVoted = voterService.hasVoted(voterId);
        if (hasVoted) {
            return "redirect:/ballot?error=alreadyVoted";
        }

        voterService.submitVote(voterId, partyId, candidateIds);

        return "redirect:/ballot/confirmation";
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "confirmation";
    }
}
