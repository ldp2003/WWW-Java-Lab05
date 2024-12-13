package iuh.edu.vn.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import iuh.edu.vn.backend.ids.CandidateSkillId;
import iuh.edu.vn.backend.ids.JobSkillId;
import iuh.edu.vn.backend.models.*;
import iuh.edu.vn.backend.repositories.CandidateRepository;
import iuh.edu.vn.backend.repositories.CandidateSkillRepository;
import iuh.edu.vn.backend.repositories.SkillRepository;
import iuh.edu.vn.backend.services.AddressService;
import iuh.edu.vn.backend.services.CandidateService;
import iuh.edu.vn.backend.services.CandidateSkillService;
import iuh.edu.vn.backend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private JobService jobService;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private CandidateSkillService candidateSkillService;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;


    @GetMapping("/candidates")
    public String showCandidateList(Model model){
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates/candidates";
    }

    @GetMapping("/listPaging")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage= candidateService.findAllPaging(currentPage - 1,pageSize,"id","asc");


        model.addAttribute("candidates", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidates/listPaging";
    }

    @GetMapping("/addCandidate")
    public String addCandidateForm(Model model){
        model.addAttribute("candidate", new Candidate());
        model.addAttribute("address", new Address());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("countryCodes", Arrays.stream(CountryCode.values())
                .collect(Collectors.toList()));
        return "candidates/addCandidate";
    }

    @PostMapping("/addCandidate")
    public String addCandidate(@ModelAttribute Candidate candidate){
        candidate.getCandidateSkills().removeIf(candidateSkill -> candidateSkill.getSkill().getId() == null);
        addressService.add(candidate.getAddress());
        candidateService.add(candidate);
        for (CandidateSkill candidateSkill : candidate.getCandidateSkills()) {
            if (candidateSkill.getSkill() != null) {
                CandidateSkillId candidateSkillId = new CandidateSkillId();
                candidateSkillId.setCanId(candidate.getId());
                candidateSkillId.setSkillId(candidateSkill.getSkill().getId());
                candidateSkill.setId(candidateSkillId);
                candidateSkill.setCandidate(candidate);
            }
        }
        candidateSkillRepository.saveAll(candidate.getCandidateSkills());
        return "redirect:/listPaging";
    }

    @GetMapping("/editCandidate/{id}")
    public String editCandidateForm(@PathVariable("id") Long id, Model model){
        Candidate candidate = candidateService.findById(id);
        model.addAttribute("candidateEditing", candidate);
        model.addAttribute("addressEditing", candidate.getAddress());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("countryCodes", Arrays.stream(CountryCode.values())
                .collect(Collectors.toList()));
        return "candidates/editCandidate";
    }

    @PostMapping("/editCandidate/{id}")
    public String editCandidate(@PathVariable("id") Long id, @ModelAttribute Candidate candidate){
        List<CandidateSkill> originalSkills = candidateSkillRepository.findByCandidateId(id);
        List<CandidateSkill> skillsToDelete = new ArrayList<>(originalSkills);
        candidate.getCandidateSkills().removeIf(candidateSkill -> candidateSkill.getSkill().getId() == null);
        addressService.add(candidate.getAddress());
        candidateRepository.save(candidate);
        for (CandidateSkill candidateSkill : candidate.getCandidateSkills()) {
            if (candidateSkill.getSkill() != null) {
                CandidateSkillId candidateSkillId = new CandidateSkillId();
                candidateSkillId.setCanId(candidate.getId());
                candidateSkillId.setSkillId(candidateSkill.getSkill().getId());
                candidateSkill.setId(candidateSkillId);
                candidateSkill.setCandidate(candidate);
            }
        }
        for (CandidateSkill candidateSkill : candidate.getCandidateSkills()) {
            skillsToDelete.removeIf(skill -> skill.getSkill().getId().equals(candidateSkill.getSkill().getId()));
        }
        candidateSkillRepository.deleteAll(skillsToDelete);
        candidateSkillRepository.saveAll(candidate.getCandidateSkills());
        return "redirect:/listPaging";
    }

//    @GetMapping("/suggestJobs/{id}")
//    public String suggestJobForCandidate(@PathVariable("id") Long id, Model model){
//        Candidate candidate = candidateService.findById(id);
//        model.addAttribute("candidateSuggesting", candidate);
//        model.addAttribute("jobsSuggesting", jobService.findMatchingJobs(candidate));
//        return "candidates/suggestJobForCandidate";
//    }

    @GetMapping("/candidateDetail/{id}")
    public String showCandidateDetail(@PathVariable("id") Long id, Model model){
        Candidate candidate = candidateService.findById(id);
        model.addAttribute("candidateDetail", candidate);
        return "candidates/candidateDetail";
    }
@GetMapping("/suggestJobs/{id}")
public String suggestJobForCandidate(@PathVariable("id") Long id,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     Model model) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(10);
    Candidate candidate = candidateService.findById(id);
    Page<Job> jobPage = jobService.findMatchingJobsPaging(candidate, currentPage - 1, pageSize);

    model.addAttribute("candidateSuggesting", candidate);
    model.addAttribute("jobsSuggesting", jobPage);

    int totalPages = jobPage.getTotalPages();
    if (totalPages > 0) {
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
    }

    return "candidates/suggestJobForCandidate";
}
}
