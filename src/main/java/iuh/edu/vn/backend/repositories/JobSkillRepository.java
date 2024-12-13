package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.ids.JobSkillId;
import iuh.edu.vn.backend.models.JobSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JobSkillRepository extends CrudRepository<JobSkill, JobSkillId>, PagingAndSortingRepository<JobSkill, JobSkillId>{
    @Query("SELECT js FROM JobSkill js WHERE js.id.jobId = ?1")
    List<JobSkill> findByJobId(Long id);
}