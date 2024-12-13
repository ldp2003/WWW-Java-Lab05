package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.dto.SkillDTO;
import iuh.edu.vn.backend.models.Candidate;
import iuh.edu.vn.backend.models.Job;
import iuh.edu.vn.backend.models.Skill;
import iuh.edu.vn.backend.repositories.CandidateRepository;
import iuh.edu.vn.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SkillRepository skillRepository;

    public Page<Candidate> findAllPaging(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return candidateRepository.findAll(pageable);//findFirst.../findTop...
    }

    public void add(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public Candidate findById(long id) {
        return candidateRepository.findById(id).get();
    }

    public void update(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public Page<Candidate> findMatchingCandidatesPaging(Job job, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<Candidate> matchingCandidates = candidateRepository.findMatchingCandidates(job.getId());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), matchingCandidates.size());
        List<Candidate> pagedCandidates = matchingCandidates.subList(start, end);

        return new PageImpl<>(pagedCandidates, pageable, matchingCandidates.size());
    }

    public Page<Candidate> searchCandidates(String searchTerm, String searchCriteria, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        switch (searchCriteria) {
            case "phone":
                return candidateRepository.findByPhoneNumberContaining(searchTerm, pageable);
            case "email":
                return candidateRepository.findByEmailContainingIgnoreCase(searchTerm, pageable);
            case "name":
            default:
                return candidateRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        }
    }

    public Page<Skill> suggestSkillsById(Long id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<SkillDTO> skills = candidateRepository.findSuggestedSkillsForCandidate(id);

        List<Skill> suggestSkills = new ArrayList<>();
        for(SkillDTO skill : skills){
            Optional<Skill> skillOptional = skillRepository.findById(skill.getId());
            skillOptional.ifPresent(suggestSkills::add);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), suggestSkills.size());
        List<Skill> pagedSkills = suggestSkills.subList(start, end);
        return new PageImpl<>(pagedSkills, pageable, suggestSkills.size());
    }
}
