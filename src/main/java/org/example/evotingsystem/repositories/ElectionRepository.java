package org.example.evotingsystem.repositories;

import org.example.evotingsystem.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    Election findByName(String name);

    List<Election> findAll();

    List<Election> findByStartDateBefore(LocalDateTime startDate);

    List<Election> findByStartDateBeforeAndEndDateAfter(LocalDateTime startDate, LocalDateTime endDate);

    List<Election> findByEndDateBefore(LocalDateTime endDate);

    boolean existsByStartDateBeforeAndEndDateAfter(LocalDateTime now, LocalDateTime now1);

    List<Election> findByIsActive(boolean status);
}
