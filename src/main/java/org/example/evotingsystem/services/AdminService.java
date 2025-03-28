package org.example.evotingsystem.services;

import org.example.evotingsystem.models.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Admin createAdmin(Admin admin);

    Admin updateAdmin(long id, Admin admin);

    void deleteAdmin(long id);

    Optional<Admin> getAdminById(long id);

    Optional<Admin> getAdminByEmail(String email);
    Optional<Admin> getAdminByUsername(String username);
    Optional<Admin> getAdminByPassword(String personalNumber);

    Optional<Admin> getAdminByName(String firstName, String lastName);

    List<Admin> getAllAdmins();

    Optional<Admin> getAdminByUsernameAndPassword(String username, String password);


    Admin getAdmin();
}