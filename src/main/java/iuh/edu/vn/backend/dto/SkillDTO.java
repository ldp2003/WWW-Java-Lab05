package iuh.edu.vn.backend.dto;

import iuh.edu.vn.backend.enums.SkillLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDTO {
    private Long id;
    private String skillName;
    private String skillType;
    private String description;
    private long jobPostings;
    private SkillLevel averageSkillLevel;

    public SkillDTO(Long id, long jobPostings, int averageSkillLevel) {
        this.id = id;
        this.jobPostings = jobPostings;
        this.averageSkillLevel = SkillLevel.values()[averageSkillLevel];
    }

}