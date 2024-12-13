package iuh.edu.vn.backend.repositories;

import iuh.edu.vn.backend.models.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends CrudRepository<Address, Long>, PagingAndSortingRepository<Address, Long>{
}