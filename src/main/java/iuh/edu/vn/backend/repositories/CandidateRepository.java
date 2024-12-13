package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.models.Candidate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidateRepository extends CrudRepository<Candidate, Long>, PagingAndSortingRepository<Candidate, Long>{
    @Query("SELECT c FROM Candidate c JOIN c.candidateSkills cs JOIN JobSkill js ON cs.skill.id = js.skill.id " +
            "WHERE js.job.id = :jobId AND cs.skillLevel >= js.skillLevel " +
            "GROUP BY c.id " +
            "HAVING COUNT(cs.id) > 0 " +
            "ORDER BY COUNT(cs.id) DESC")
    List<Candidate> findMatchingCandidates(@Param("jobId") Long jobId);
}