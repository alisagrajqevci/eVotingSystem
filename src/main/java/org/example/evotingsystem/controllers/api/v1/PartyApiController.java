package org.example.evotingsystem.controllers.api.v1;

import org.example.evotingsystem.models.Party;
import org.example.evotingsystem.services.PartyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parties")
public class PartyApiController {

    private final PartyService partyService;

    public PartyApiController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping
    public List<Party> getAllParties() {
        return partyService.getAllParties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Party> getPartyById(@PathVariable long id) {
        return partyService.getPartyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public Party getPartyByName(@PathVariable String name) {
        return partyService.getPartyByName(name);
    }

    @GetMapping("/search/by-description/{description}")
    public Party getPartyByDescription(@PathVariable String description) {
        return partyService.getPartyByDescription(description);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Party createParty(@Valid @RequestBody Party party) {
        return partyService.createParty(party);
    }

    @PutMapping("/{id}")
    public Party updateParty(@PathVariable long id, @Valid @RequestBody Party updatedParty) {
        return partyService.updateParty(id, updatedParty);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParty(@PathVariable long id) {
        partyService.deleteParty(id);
    }
}

