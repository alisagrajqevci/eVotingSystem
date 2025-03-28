package org.example.evotingsystem.exceptions;

public class CandidateNotFoundException extends RuntimeException {
    public CandidateNotFoundException(long id) {
        super("Candidate not found with ID: " + id);
    }
}
