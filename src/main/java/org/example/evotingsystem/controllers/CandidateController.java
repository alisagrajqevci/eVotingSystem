package org.example.evotingsystem.controllers;

import org.example.evotingsystem.helpers.MessageHelper;
import org.example.evotingsystem.models.Admin;
import org.example.evotingsystem.models.Candidate;
import org.example.evotingsystem.models.Election;
import org.example.evotingsystem.models.Party;
import org.example.evotingsystem.services.AdminService;
import org.example.evotingsystem.services.CandidateService;
import org.example.evotingsystem.services.ElectionService;
import org.example.evotingsystem.services.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private ElectionService electionService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/candidate/create")
    public String createCandidate(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("party.id") Long partyId,
            @RequestParam("election.id") Long electionId,
            @RequestParam("numberInParty") int numberInParty,
            Model model) {
        try {
            Optional<Party> partyOpt = partyService.getPartyById(partyId);
            Optional<Election> electionOpt = electionService.getElectionById(electionId);

            if (partyOpt.isPresent() && electionOpt.isPresent()) {
                if (candidateService.isNumberInPartyDuplicate(partyId, numberInParty)) {
                    MessageHelper.addErrorMessage(model, "Ky numër është tashmë në përdorim në këtë parti.");
                    return "redirect:/admin/dashboard#manageCandidates";
                }

                Candidate candidate = new Candidate();
                candidate.setFirstName(firstName);
                candidate.setLastName(lastName);
                candidate.setParty(partyOpt.get());
                candidate.setElection(electionOpt.get());
                Admin admin = adminService.getAdmin();
                candidate.setCreatedBy(String.valueOf(admin.getId()));

                candidate.setNumberInParty(numberInParty);

                candidateService.createCandidate(candidate);
                MessageHelper.addSuccessMessage(model, "Kandidati u krijua me sukses!");
                return "redirect:/admin/dashboard#manageCandidates";
            } else {
                MessageHelper.addErrorMessage(model, "Partia ose zgjedhja nuk u gjet.");
                return "admin/create-candidate";
            }
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë krijimit të kandidatit.");
            return "admin/create-candidate";
        }
    }

    @PostMapping("/candidate/{id}/edit")
    public String updateCandidate(@PathVariable Long id,
                                  @RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam("party.id") Long partyId,
                                  @RequestParam("election.id") Long electionId,
                                  @RequestParam("numberInParty") int numberInParty,
                                  Model model) {
        try {
            Optional<Party> partyOpt = partyService.getPartyById(partyId);
            Optional<Election> electionOpt = electionService.getElectionById(electionId);

            if (partyOpt.isPresent() && electionOpt.isPresent()) {
                Optional<Candidate> candidateOpt = candidateService.getCandidateById(id);
                if (candidateOpt.isPresent()) {
                    if (candidateService.isNumberInPartyDuplicate(partyId, numberInParty)) {
                        MessageHelper.addErrorMessage(model, "Ky numër është tashmë në përdorim në këtë parti.");
                        return "admin/edit-candidate";
                    }

                    Candidate candidate = candidateOpt.get();
                    candidate.setFirstName(firstName);
                    candidate.setLastName(lastName);
                    candidate.setParty(partyOpt.get());
                    candidate.setElection(electionOpt.get());
                    candidate.setNumberInParty(numberInParty);

                    candidateService.updateCandidate(id, candidate);
                    MessageHelper.addSuccessMessage(model, "Kandidati u përditësua me sukses!");
                    return "redirect:/admin/dashboard#manageCandidates";
                } else {
                    MessageHelper.addErrorMessage(model, "Kandidati nuk u gjet.");
                    return "admin/edit-candidate";
                }
            } else {
                MessageHelper.addErrorMessage(model, "Partia ose zgjedhja nuk u gjet.");
                return "admin/edit-candidate";
            }
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë përditësimit të kandidatit.");
            return "admin/edit-candidate";
        }
    }
}
