package iuh.edu.vn.backend.models;

import iuh.edu.vn.backend.enums.SkillType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "skill", schema = "works")
@NamedQueries({
        @NamedQuery(name = "Skill.findBySkillNameContainsIgnoreCase", query = "select s from Skill s where upper(s.skillName) like upper(concat('%', :skillName, '%'))")
})
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Long id;

    @Column(name = "skill_description")
    private String skillDescription;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "type")
    private SkillType skillType;

    @OneToMany(mappedBy = "skill")
    private List<CandidateSkill> candidateSkills = new ArrayList<>();

    @OneToMany(mappedBy = "skill")
    private List<JobSkill> jobSkills = new ArrayList<>();

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", skillDescription='" + skillDescription + '\'' +
                ", skillName='" + skillName + '\'' +
                ", skillType=" + skillType +
                '}';
    }
}