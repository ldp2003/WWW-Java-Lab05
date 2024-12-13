package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.ids.CandidateSkillId;
import iuh.edu.vn.backend.models.CandidateSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CandidateSkillRepository extends CrudRepository<CandidateSkill, CandidateSkillId>, PagingAndSortingRepository<CandidateSkill, CandidateSkillId>{
   @Query("SELECT cs FROM CandidateSkill cs WHERE cs.id.canId = ?1")
    List<CandidateSkill> findByCandidateId(Long id);
}