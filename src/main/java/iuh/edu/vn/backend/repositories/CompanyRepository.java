package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.models.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends CrudRepository<Company, Long>, PagingAndSortingRepository<Company, Long>{
}