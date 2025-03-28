package org.example.evotingsystem.repositories;

import org.example.evotingsystem.models.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    Party findByName(String name);

    Party findByDescription(String description);

    List<Party> findAll();

}