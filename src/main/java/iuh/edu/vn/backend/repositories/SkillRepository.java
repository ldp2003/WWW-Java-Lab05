package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.models.Job;
import iuh.edu.vn.backend.models.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill, Long>, PagingAndSortingRepository<Skill, Long>{

    @Query("select s from Skill s where upper(s.skillName) like upper(concat('%', ?1, '%'))")
    List<Skill> findBySkillNameContainsIgnoreCase(String skillName);


}