package iuh.edu.vn.backend.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "experience", schema = "giuakycandidate")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id", nullable = false)
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "work_desc", nullable = false, length = 400)
    private String workDesc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "can_id", nullable = false)
    private Candidate candidate;

    @Column(name = "company", nullable = false, length = 120)
    private String company;

    public Experience(LocalDate fromDate, String role, LocalDate toDate, String workDesc, Candidate candidate, String company) {
        this.fromDate = fromDate;
        this.role = role;
        this.toDate = toDate;
        this.workDesc = workDesc;
        this.candidate = candidate;
        this.company = company;
    }
}