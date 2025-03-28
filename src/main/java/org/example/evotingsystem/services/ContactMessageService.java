package org.example.evotingsystem.services;

import org.example.evotingsystem.models.ContactMessage;

import java.util.List;
import java.util.Optional;

public interface ContactMessageService {

    List<ContactMessage> getAllMessages();

    Optional<ContactMessage> getMessageById(long id);

    List<ContactMessage> getMessagesByNameAndEmail(String name, String email);
}
