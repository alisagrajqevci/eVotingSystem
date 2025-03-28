package org.example.evotingsystem.controllers.api.v1;

import org.example.evotingsystem.models.ContactMessage;
import org.example.evotingsystem.services.ContactMessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/admin-dashboard/feedback")

public class ContactMessageApiController {

    private final ContactMessageService service;

    public ContactMessageApiController(ContactMessageService service) {
        this.service = service;
    }

    @GetMapping
    public List<ContactMessage> findAll() {
        return service.getAllMessages();
    }

    @GetMapping("/{id}")
    public Optional<ContactMessage> findById(
            @Valid @PositiveOrZero(message = "Id must be positive or zero")
            @PathVariable long id) {
        return service.getMessageById(id);
    }

    @GetMapping("/feedback")
    public List<ContactMessage> getAllFeedback() {
        return service.getAllMessages();
    }
}
