package iuh.edu.vn.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import iuh.edu.vn.backend.models.Address;
import iuh.edu.vn.backend.models.Company;
import iuh.edu.vn.backend.models.Job;
import iuh.edu.vn.backend.repositories.CompanyRepository;
import iuh.edu.vn.backend.services.AddressService;
import iuh.edu.vn.backend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyService companyService;
    @Autowired
    AddressService addressService;

    @GetMapping("/companies")
    public String showJobListPaging(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Company> companyPage = companyService.findAllPaging(currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("companies", companyPage);
        int totalPages = companyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "companies/companies";
    }

    @GetMapping("/seeCompanyDetail/{id}")
    public String seeDetailCompany(Model model, @PathVariable("id") Long id) {
        Company company = companyRepository.findById(id).get();
        model.addAttribute("company", company);
        return "companies/companyDetail";
    }

    @GetMapping("/addCompany")
    public String addCompanyForm(Model model){
        model.addAttribute("company", new Company());
        model.addAttribute("address", new Address());
        model.addAttribute("countryCodes", Arrays.stream(CountryCode.values())
                .collect(Collectors.toList()));
        return "companies/addCompany";
    }

    @PostMapping("/addCompany")
    public String addCompany(@ModelAttribute Company company){
        addressService.add(company.getAddress());
        companyService.add(company);
        return "redirect:/companies";
    }

    @GetMapping("/editCompany/{id}")
    public String showEditCompanyForm(@PathVariable("id") Long id, Model model) {
        Company company = companyService.findById(id);
        model.addAttribute("companyEditing", company);
        model.addAttribute("address", new Address());
        model.addAttribute("countryCodes", Arrays.stream(CountryCode.values())
                .collect(Collectors.toList()));
        return "companies/editCompany";
    }

    @PostMapping("/editCompany/{id}")
    public String editCompany(@PathVariable("id") Long id, @ModelAttribute Company company) {
        addressService.add(company.getAddress());
        companyService.update(company);
        return "redirect:/companies";
    }
}
