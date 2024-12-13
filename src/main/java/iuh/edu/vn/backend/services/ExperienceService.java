package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.Candidate;
import iuh.edu.vn.backend.models.Experience;
import iuh.edu.vn.backend.repositories.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    public void add(Experience experience) {
        experienceRepository.save(experience);
    }

    public void update(Experience experience) {
        experienceRepository.save(experience);
    }

    public void delete(Experience experience) {
        experienceRepository.delete(experience);
    }

    public Page<Experience> findAllPaging(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return experienceRepository.findAll(pageable);//findFirst.../findTop...
    }
}
