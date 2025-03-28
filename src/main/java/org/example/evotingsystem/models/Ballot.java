package org.example.evotingsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ballots")
public class Ballot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Voter voter;

    @ManyToOne
    private Party party;

    @ManyToOne
    private Election election;

    @ManyToMany
    @JoinTable(
            name = "ballots_candidates_voted_for",
            joinColumns = @JoinColumn(name = "ballot_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private List<Candidate> candidatesVotedFor;

    @ManyToMany
    @JoinTable(
            name = "ballots_parties_voted_for",
            joinColumns = @JoinColumn(name = "ballot_id"),
            inverseJoinColumns = @JoinColumn(name = "party_id")
    )
    private List<Party> partiesVotedFor;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

}