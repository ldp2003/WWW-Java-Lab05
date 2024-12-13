package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.Candidate;
import iuh.edu.vn.backend.models.Job;
import iuh.edu.vn.backend.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
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
}
