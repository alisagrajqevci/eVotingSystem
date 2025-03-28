package org.example.evotingsystem.repositories;

import org.example.evotingsystem.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<org.example.evotingsystem.models.Admin, Long> {

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByUsername(String firstName);

    Optional<Admin> findByPassword(String password);

    Optional<Admin> findByUsernameAndPassword(String username, String password);

    Optional<Admin> findByFirstNameAndLastName(String firstName, String lastName);

    List<Admin> findAll();
}
