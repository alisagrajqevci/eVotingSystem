package org.example.evotingsystem.controllers;

import org.example.evotingsystem.helpers.MessageHelper;
import org.example.evotingsystem.models.Election;
import org.example.evotingsystem.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping("/create")
    public String createElection(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Model model) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            LocalDateTime start = LocalDateTime.parse(startDate, formatter);
            LocalDateTime end = LocalDateTime.parse(endDate, formatter);
            if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
                model.addAttribute("errorMessage", "Zgjedhja nuk mund te krijohet ne te kaluaren!");
                return "admin/create-election";
            }

            Election election = new Election();
            election.setName(name);
            election.setDescription(description);
            election.setStartDate(start);
            election.setEndDate(end);


            electionService.createElection(election);

            MessageHelper.addSuccessMessage(model, "Zgjedhja e re u krijua me sukses!");
            return "redirect:/admin/dashboard#manage";
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë krijimit të zgjedhjes.");
            return "admin/create-election";
        }
    }

    @PostMapping("/election/{id}/edit")
    public String updateElection(@PathVariable Long id,
                                 @RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("startDate") String startDate,
                                 @RequestParam("endDate") String endDate,
                                 Model model) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


            LocalDateTime start = LocalDateTime.parse(startDate, formatter);
            LocalDateTime end = LocalDateTime.parse(endDate, formatter);

            Optional<Election> electionOpt = electionService.getElectionById(id);
            if (electionOpt.isPresent()) {
                Election election = electionOpt.get();
                election.setName(name);
                election.setDescription(description);
                election.setStartDate(start);
                election.setEndDate(end);


                electionService.updateElection(id, election);

                MessageHelper.addSuccessMessage(model, "Zgjedhja u përditësua me sukses!");
                return "redirect:/admin/dashboard#manage";
            } else {
                MessageHelper.addErrorMessage(model, "Zgjedhja nuk u gjet.");
                return "admin/edit-election";
            }
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë përditësimit të zgjedhjes.");
            return "admin/edit-election";
        }
    }

    @PostMapping("/election/{id}/delete")
    public String deleteElection(@PathVariable Long id, Model model) {
        try {
            Optional<Election> electionOpt = electionService.getElectionById(id);
            if (electionOpt.isPresent()) {
                electionService.deleteElection(id);

                MessageHelper.addSuccessMessage(model, "Zgjedhja u fshi me sukses!");
            } else {
                MessageHelper.addErrorMessage(model, "Zgjedhja nuk u gjet.");
            }
            return "redirect:/admin/dashboard#manage";
        } catch (Exception e) {
            MessageHelper.addErrorMessage(model, "Ka ndodhur një gabim gjatë fshirjes së zgjedhjes.");
            return "redirect:/admin/dashboard#manage";
        }
    }
}

