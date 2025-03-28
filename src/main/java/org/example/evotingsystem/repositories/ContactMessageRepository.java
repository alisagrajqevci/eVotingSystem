package org.example.evotingsystem.repositories;

import org.example.evotingsystem.models.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findByNameAndEmail(String name, String email);
}
