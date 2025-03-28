package org.example.evotingsystem.controllers.api.v1;

import org.example.evotingsystem.dto.BallotDTO;
import org.example.evotingsystem.services.BallotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ballots")
public class BallotApiController {

    @Autowired
    private BallotService ballotService;

    @PostMapping
    public ResponseEntity<String> submitBallot(@RequestBody BallotDTO ballotDTO) {
        try {
            ballotService.createBallot(ballotDTO);
            return ResponseEntity.ok("Vote submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to submit vote: " + e.getMessage());
        }
    }
}
