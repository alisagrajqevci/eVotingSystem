package org.example.evotingsystem.controllers.api.v1;

import org.example.evotingsystem.models.Candidate;
import org.example.evotingsystem.services.CandidateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateApiController {

    private final CandidateService service;

    public CandidateApiController(CandidateService service) {
        this.service = service;
    }

    @GetMapping
    public List<Candidate> findAll() {
        return service.getAllCandidates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> findById(@Valid @PositiveOrZero(message = "Id must be positive or zero") @PathVariable long id) {
        return service.getCandidateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Candidate add(@Valid @RequestBody Candidate candidate) {
        return service.createCandidate(candidate);
    }

    @PutMapping("/{id}")
    public Candidate modify(@PathVariable long id, @Valid @RequestBody Candidate updatedCandidate) {
        return service.updateCandidate(id, updatedCandidate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.deleteCandidate(id);
    }

    @GetMapping("/search")
    public List<Candidate> findByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        return service.getCandidatesByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/party/id/{partyId}")
    public List<Candidate> findByPartyId(@PathVariable long partyId) {
        return service.getCandidatesByPartyId(partyId);
    }

    @GetMapping("/party/name/{partyName}")
    public List<Candidate> getCandidatesByPartyName(@PathVariable String partyName) {
        return service.getCandidatesByPartyName(partyName);
    }

    @GetMapping("/election/{electionId}")
    public List<Candidate> findByElectionId(@PathVariable long electionId) {
        return service.getCandidatesByElectionId(electionId);
    }

    @GetMapping("/election/{electionId}/party/{partyId}")
    public List<Candidate> findByElectionIdAndPartyId(@PathVariable long electionId, @PathVariable long partyId) {
        return service.getCandidatesByElectionIdAndPartyId(electionId, partyId);
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<Candidate>> getCandidatesByParty(@PathVariable Long partyId) {
        List<Candidate> candidates = service.getCandidatesByParty(partyId);
        return ResponseEntity.ok(candidates);
    }
}
