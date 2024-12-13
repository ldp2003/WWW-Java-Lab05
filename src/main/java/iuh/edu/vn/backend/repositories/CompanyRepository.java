package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Long>, PagingAndSortingRepository<Company, Long>{
    @Query("SELECT c FROM Company c WHERE LOWER(c.compName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Company> findByNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT c FROM Company c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Company> findByEmailContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT c FROM Company c WHERE c.phone LIKE CONCAT('%', :searchTerm, '%')")
    Page<Company> findByPhoneNumberContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
}