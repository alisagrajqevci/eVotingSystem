package org.example.evotingsystem.controllers;

import org.example.evotingsystem.models.*;
import org.example.evotingsystem.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ElectionService electionService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private PartyService partyService;

    @PostMapping("/login")
    public String loginAdmin(@ModelAttribute Admin admin, HttpSession session, Model model) {
        Optional<Admin> adminOptional = adminService.getAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());

        if (adminOptional.isPresent()) {
            session.setAttribute("admin", adminOptional.get());
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Të dhënat nuk u gjetën. Ju lutem kontrolloni dhe provoni përsëri!");
            return "admin/admin-login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Election> elections = electionService.getAllElections();
        List<Candidate> candidates = candidateService.getAllCandidates();
        List<Party> parties = partyService.getAllParties();

        model.addAttribute("admin", admin);
        model.addAttribute("elections", elections);
        model.addAttribute("candidates", candidates);
        model.addAttribute("parties", parties);
        return "admin/admin-dashboard";
    }

    @GetMapping("/create")
    public String createElection(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "admin/create-election";
    }

    @GetMapping("/election/{id}/edit")
    public String editElection(@PathVariable Long id, Model model) {
        Optional<Election> electionOpt = electionService.getElectionById(id);
        if (electionOpt.isPresent()) {
            Election election = electionOpt.get();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String startDateFormatted = election.getStartDate().format(formatter);
            String endDateFormatted = election.getEndDate().format(formatter);

            model.addAttribute("election", election);
            model.addAttribute("startDateFormatted", startDateFormatted);
            model.addAttribute("endDateFormatted", endDateFormatted);

            return "admin/edit-election";
        } else {
            model.addAttribute("error", "Zgjedhja nuk u gjet.");
            return "admin/admin-dashboard";
        }
    }

    @GetMapping("/party/create")
    public String createParty(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin", admin);
        model.addAttribute("elections", electionService.getAllElections());
        return "admin/create-party";
    }

    @GetMapping("/party/{id}/edit")
    public String editParty(@PathVariable Long id, Model model) {
        Optional<Party> partyOpt = partyService.getPartyById(id);
        if (partyOpt.isPresent()) {
            Party party = partyOpt.get();
            model.addAttribute("party", party);
            return "admin/edit-party";
        } else {
            model.addAttribute("error", "Partia nuk u gjet.");
            return "admin/admin-dashboard";
        }
    }

    @GetMapping("/candidate/create")
    public String createCandidate(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Election> elections = electionService.getAllElections();
        List<Party> parties = partyService.getAllParties();
        model.addAttribute("admin", admin);
        model.addAttribute("elections", elections);
        model.addAttribute("parties", parties);
        return "admin/create-candidate";
    }

    @GetMapping("/candidate/{id}/edit")
    public String editCandidate(@PathVariable Long id, Model model) {
        Optional<Candidate> candidateOpt = candidateService.getCandidateById(id);
        if (candidateOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            model.addAttribute("candidate", candidate);
            model.addAttribute("elections", electionService.getAllElections());
            model.addAttribute("parties", partyService.getAllParties());
            return "admin/edit-candidate";
        } else {
            model.addAttribute("error", "Kandidati nuk u gjet.");
            return "admin/admin-dashboard";
        }
    }

    @PostMapping("/election/{id}/toggle-status")
    public String toggleElectionStatus(@PathVariable Long id, HttpSession session, Model model) {
        Optional<Election> electionOpt = electionService.getElectionById(id);
        if (electionOpt.isPresent()) {
            Election election = electionOpt.get();
            election.setActive(!election.isActive());
            electionService.saveElection(election);
        } else {
            model.addAttribute("error", "Zgjedhja nuk u gjet.");
        }
        return "redirect:/admin/dashboard";
    }

}
