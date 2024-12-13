package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.CandidateSkill;
import iuh.edu.vn.backend.repositories.CandidateSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateSkillService {
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    public void add(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }

    public void update(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }

    public void delete(CandidateSkill candidateSkill) {
        candidateSkillRepository.delete(candidateSkill);
    }


}
