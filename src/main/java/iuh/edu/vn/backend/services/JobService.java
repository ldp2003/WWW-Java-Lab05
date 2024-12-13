package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.Candidate;
import iuh.edu.vn.backend.models.Job;
import iuh.edu.vn.backend.repositories.CompanyRepository;
import iuh.edu.vn.backend.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public void add(Job job) {
        jobRepository.save(job);
    }

    public void update(Job job) {
        jobRepository.save(job);
    }

    public void delete(Job job) {
        jobRepository.delete(job);
    }


    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
    public Page<Job> findAllPaging(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findAll(pageable);//findFirst.../findTop...
    }

    public List<Job> findMatchingJobs(Candidate candidate) {
        return jobRepository.findAll().stream()
                .filter(job -> job.getJobSkills().stream()
                        .allMatch(jobSkill -> candidate.getCandidateSkills().stream()
                                .anyMatch(candidateSkill -> candidateSkill.getSkill().getId().equals(jobSkill.getSkill().getId()) &&
                                        candidateSkill.getSkillLevel().getValue() >= jobSkill.getSkillLevel().getValue())))
                .collect(Collectors.toList());
    }

    //    public Page<Job> findMatchingJobsPaging(Candidate candidate, int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//
//        if (candidate.getCandidateSkills().isEmpty()) {
//            return new PageImpl<>(Collections.emptyList(), pageable, 0);
//        }
//
//        List<Job> filteredJobs = jobRepository.findAll().stream()
//                .filter(job -> job.getJobSkills().stream()
//                        .allMatch(jobSkill -> candidate.getCandidateSkills().stream()
//                                .anyMatch(candidateSkill -> candidateSkill.getSkill().getId().equals(jobSkill.getSkill().getId()) &&
//                                        candidateSkill.getSkillLevel().getValue() >= jobSkill.getSkillLevel().getValue())))
//                .collect(Collectors.toList());
//
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), filteredJobs.size());
//        List<Job> pagedJobs = filteredJobs.subList(start, end);
//
//        return new PageImpl<>(pagedJobs, pageable, filteredJobs.size());
//    }
    public Page<Job> findMatchingJobsPaging(Candidate candidate, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        if (candidate.getCandidateSkills().isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<Job> matchingJobs = jobRepository.findMatchingJobs(candidate.getId());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), matchingJobs.size());
        List<Job> pagedJobs = matchingJobs.subList(start, end);

        return new PageImpl<>(pagedJobs, pageable, matchingJobs.size());
    }
}
