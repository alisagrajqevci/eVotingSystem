package org.example.evotingsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name cannot exceed 50 characters.")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot exceed 50 characters.")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Username is required.")
    @Size(max = 50, message = "Username cannot exceed 50 characters.")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(max = 100, message = "Password cannot exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String password;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    @Size(max = 100, message = "Email cannot exceed 100 characters.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private String createdBy;

    @Column
    private LocalDateTime modifiedAt;

    @Column
    private String modifiedBy;

}

