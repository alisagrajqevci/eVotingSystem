package org.example.evotingsystem.services.impls;

import org.example.evotingsystem.models.ContactMessage;
import org.example.evotingsystem.repositories.ContactMessageRepository;
import org.example.evotingsystem.services.ContactMessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository repository;

    public ContactMessageServiceImpl(ContactMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ContactMessage> getAllMessages() {
        return repository.findAll();
    }

    @Override
    public Optional<ContactMessage> getMessageById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<ContactMessage> getMessagesByNameAndEmail(String name, String email) {
        return repository.findByNameAndEmail(name, email);
    }
}
