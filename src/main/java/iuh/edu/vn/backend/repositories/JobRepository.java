package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.models.Candidate;
import iuh.edu.vn.backend.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long>, PagingAndSortingRepository<Job, Long>{

    @Query("SELECT j FROM Job j JOIN j.jobSkills js JOIN CandidateSkill cs ON js.skill.id = cs.skill.id " +
            "WHERE cs.candidate.id = :candidateId AND cs.skillLevel >= js.skillLevel " +
            "GROUP BY j.id " +
            "HAVING COUNT(js.id) > 0 " +
            "ORDER BY COUNT(js.id) DESC")
    List<Job> findMatchingJobs(@Param("candidateId") Long candidateId);

    @Query("SELECT j FROM Job j WHERE LOWER(j.jobName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(j.jobDesc) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Job> findByKeywordContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE LOWER(j.company.compName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Job> findByCompanyContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);
}