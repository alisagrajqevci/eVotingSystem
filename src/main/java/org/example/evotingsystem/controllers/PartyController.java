package org.example.evotingsystem.controllers;

import org.example.evotingsystem.helpers.MessageHelper;
import org.example.evotingsystem.models.Election;
import org.example.evotingsystem.models.Party;
import org.example.evotingsystem.services.ElectionService;
import org.example.evotingsystem.services.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @Autowired
    private ElectionService electionService;

    @PostMapping("/party/create")
    public String createParty(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("electionId") long electionId,
                              Model model) {
        try {
            Party party = new Party();
            party.setName(name);
            party.setDescription(description);

            Optional<Election> electionOpt = electionService.getElectionById(electionId);
            if (electionOpt.isPresent()) {
                party.setElection(electionOpt.get());
                partyService.createParty(party);

                MessageHelper.addSuccessMessage(model, "Partia u krijua me sukses!");
                return "redirect:/admin/dashboard#manageParties";
            } else {
                MessageHelper.addErrorMessage(model, "Zgjedhja nuk u gjet.");
                return "admin/create-party";
            }
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë krijimit të partisë.");
            return "admin/create-party";
        }
    }

    @PostMapping("/party/{id}/edit")
    public String updateParty(@PathVariable Long id, @RequestParam("name") String name, Model model) {
        try {
            Optional<Party> partyOpt = partyService.getPartyById(id);
            if (partyOpt.isPresent()) {
                Party party = partyOpt.get();
                party.setName(name);
                partyService.updateParty(id, party);

                MessageHelper.addSuccessMessage(model, "Partia u përditësua me sukses!");
                return "redirect:/admin/dashboard#manageParties";
            } else {
                MessageHelper.addErrorMessage(model, "Partia nuk u gjet.");
                return "admin/edit-party";
            }
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë përditësimit të partisë.");
            return "admin/edit-party";
        }
    }

    @PostMapping("/party/{id}/delete")
    public String deleteParty(@PathVariable Long id, Model model) {
        try {
            Optional<Party> partyOpt = partyService.getPartyById(id);
            if (partyOpt.isPresent()) {
                partyService.deleteParty(id);
                MessageHelper.addSuccessMessage(model, "Partia u fshi me sukses!");
            } else {
                MessageHelper.addErrorMessage(model, "Partia nuk u gjet.");
            }
            return "redirect:/admin/dashboard#manageParties";
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë fshirjes së partisë.");
            return "redirect:/admin/dashboard#manageParties";
        }
    }
}