package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.Company;
import iuh.edu.vn.backend.models.Skill;
import iuh.edu.vn.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public void add(Skill skill) {
        skillRepository.save(skill);
    }

    public void update(Skill skill) {
        skillRepository.save(skill);
    }

    public void delete(Skill skill) {
        skillRepository.delete(skill);
    }

    public Page<Skill> findAllPaging(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return skillRepository.findAll(pageable);
    }

    public List<Skill> findByNameContaining(String name) {
        return skillRepository.findBySkillNameContainsIgnoreCase(name);
    }
}
