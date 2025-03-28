package org.example.evotingsystem.controllers;

import org.example.evotingsystem.models.*;
import org.example.evotingsystem.services.ElectionService;
import org.example.evotingsystem.services.PartyService;
import org.example.evotingsystem.services.VoterService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private VoterService voterService;

    @Autowired
    private ElectionService electionService;

    @Autowired
    private PartyService partyService;

    @GetMapping("/")
    public String index(Model model) {

        boolean isActive = !electionService.getElectionsByStatus(true).isEmpty();

        model.addAttribute("isActive", isActive);

        return "index";
    }


    @GetMapping("/parties")
    public String parties() {
        return "parties";
    }

    @GetMapping("/candidates")
    public String candidates() {
        return "candidates";
    }

    @GetMapping("/pastElections")
    public String pastElections() {
        return "pastElections";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("voter", new Voter());
        return "loginForm";
    }

    @PostMapping("/login")
    public String validateVoter(@Valid Voter voter, BindingResult result, Model model, HttpSession session) {
        String personalNumber = voter.getPersonalNumber();
        if (personalNumber == null || personalNumber.length() != 10 || !personalNumber.matches("\\d+")) {
            result.rejectValue("personalNumber", "error.personalNumber", "Numri personal duhet të jetë 10 shifra.");
        }

        LocalDate birthdate = voter.getBirthdate();
        if (birthdate == null || Period.between(birthdate, LocalDate.now()).getYears() < 18) {
            result.rejectValue("birthdate", "error.birthdate", "Duhet të jeni mbi 18 vjeç.");
        }

        if (result.hasErrors()) {
            return "loginForm";
        }

        Voter foundVoter = voterService.getVoterByPersonalNumberAndBirthdate(personalNumber, birthdate);
        if (foundVoter == null) {
            model.addAttribute("error", "Të dhënat nuk u gjetën. Ju lutem kontrolloni dhe provoni përsëri.");
            return "loginForm";
        }

        if (foundVoter.getHasVoted()) {
            model.addAttribute("error", "Ju keni votuar!");
            return "loginForm";
        }

        voterService.markVoterAsLoggedIn(foundVoter.getId());

        session.setAttribute("voterId", foundVoter.getId());

        return "redirect:/ballot";
    }

    @GetMapping("/ballot")
    public String showBallot(Model model, HttpSession session) {
        Long voterId = (Long) session.getAttribute("voterId");
        if (voterId == null) {
            return "redirect:/login";
        }

        Optional<Voter> voterOptional = voterService.getVoterById(voterId);
        if (!voterOptional.isPresent()) {
            return "redirect:/login";
        }

        Voter voter = voterOptional.get();

        List<Party> parties = partyService.getAllParties();
        model.addAttribute("parties", parties);
        model.addAttribute("voter", voter);

        return "ballot";
    }

    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/admin-login";
    }
}
