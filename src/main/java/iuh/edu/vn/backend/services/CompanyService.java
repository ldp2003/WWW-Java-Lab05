package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.Candidate;
import iuh.edu.vn.backend.models.Company;
import iuh.edu.vn.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public void add(Company company) {
        companyRepository.save(company);
    }

    public void update(Company company) {
        companyRepository.save(company);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }

    public Page<Company> findAllPaging(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return companyRepository.findAll(pageable);
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

}
