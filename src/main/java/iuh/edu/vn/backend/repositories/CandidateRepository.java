package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.dto.SkillDTO;
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

    @Query("SELECT new iuh.edu.vn.backend.dto.SkillDTO(s.id, COUNT(js.id), CAST(AVG(js.skillLevel) AS int)) " +
            "FROM Skill s LEFT JOIN JobSkill js ON s.id = js.skill.id " +
            "WHERE s.id NOT IN (SELECT cs.skill.id FROM CandidateSkill cs WHERE cs.candidate.id = :candidateId) " +
            "OR s.id IN (SELECT cs.skill.id FROM CandidateSkill cs WHERE cs.candidate.id = :candidateId AND Cast(cs.skillLevel as int) < (SELECT CAST(AVG(js.skillLevel) AS int) FROM JobSkill js WHERE js.skill.id = s.id)) " +
            "GROUP BY s.id " +
            "HAVING COUNT(js.id) >= 2 " +
            "ORDER BY COUNT(js.id) DESC")
    List<SkillDTO> findSuggestedSkillsForCandidate(@Param("candidateId") Long candidateId);


}