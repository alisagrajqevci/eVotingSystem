package org.example.evotingsystem.services.impls;

import org.example.evotingsystem.models.Admin;
import org.example.evotingsystem.repositories.AdminRepository;
import org.example.evotingsystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin createAdmin(Admin admin) {
        if (admin.getUsername() == null || admin.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(long id, Admin admin) {
        return adminRepository.findById(id)
                .map(existingAdmin -> {
                    existingAdmin.setFirstName(admin.getFirstName());
                    existingAdmin.setLastName(admin.getLastName());
                    existingAdmin.setEmail(admin.getEmail());
                    existingAdmin.setUsername(admin.getUsername());
                    existingAdmin.setPassword(admin.getPassword());
                    return adminRepository.save(existingAdmin);
                })
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));
    }

    @Override
    public void deleteAdmin(long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Optional<Admin> getAdminById(long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Optional<Admin> getAdminByUsername(String username) {

        return adminRepository.findByUsername(username);
    }

    @Override
    public Optional<Admin> getAdminByPassword(String personalNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<Admin> getAdminByName(String firstName, String lastName) {
        return adminRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminByUsernameAndPassword(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Admin getAdmin() {
        return adminRepository.findById(1L) // Assuming admin has ID 1
                .orElseThrow(() -> new RuntimeException("Admin user not found"));
    }

}
