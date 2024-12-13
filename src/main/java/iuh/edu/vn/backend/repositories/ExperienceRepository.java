package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.models.Experience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExperienceRepository extends CrudRepository<Experience, Long>, PagingAndSortingRepository<Experience, Long>{
}