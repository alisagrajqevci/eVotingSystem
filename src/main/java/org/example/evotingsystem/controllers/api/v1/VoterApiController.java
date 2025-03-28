package org.example.evotingsystem.controllers.api.v1;

import org.example.evotingsystem.services.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voters")
public class VoterApiController {

    @Autowired
    private VoterService voterService;

    @GetMapping("/{voterId}/isLoggedIn")
    public ResponseEntity<Boolean> isVoterLoggedIn(@PathVariable Long voterId) {
        boolean isLoggedIn = voterService.isVoterLoggedIn(voterId);
        return ResponseEntity.ok(isLoggedIn);
    }
}
