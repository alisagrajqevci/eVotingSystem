package org.example.evotingsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BallotDTO {
    @PositiveOrZero(message = "Id should be a positive number")
    private long id;

    @NotNull
    private long partyId;

    @NotNull(message = "Voter ID should not be null")
    private long voterId;


    @NotNull(message = "Candidates voted for should not be null")
    private List<Long> candidateIds;


    @NotNull(message = "Candidates voted for should not be null")
    private List<Long> partyIds;

    @NotNull(message = "Submission time should not be null")
    private LocalDateTime submittedAt = LocalDateTime.now();
}
