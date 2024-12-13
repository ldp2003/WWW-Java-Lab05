package iuh.edu.vn.backend.models;

import iuh.edu.vn.backend.ids.CandidateSkillId;
import iuh.edu.vn.backend.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "candidate_skill", schema = "works")
public class CandidateSkill {
    @EmbeddedId
    private CandidateSkillId id;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @MapsId("canId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "can_id", nullable = false)
    private Candidate candidate;

    @Column(name = "more_infos", length = 1000)
    private String moreInfos;

    @Column(name = "skill_level", nullable = false)
    private SkillLevel skillLevel;

    @Override
    public String toString() {
        return "CandidateSkill{" +
                "id=" + id +
                ", moreInfos='" + moreInfos + '\'' +
                ", skillLevel=" + skillLevel +
                '}';
    }
}