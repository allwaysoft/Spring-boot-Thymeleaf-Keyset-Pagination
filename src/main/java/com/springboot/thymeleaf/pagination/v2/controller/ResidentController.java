package com.springboot.thymeleaf.pagination.v2.controller;

import com.springboot.thymeleaf.pagination.v2.model.Resident;
import com.springboot.thymeleaf.pagination.v2.repository.ResidentRepository;
import com.springboot.thymeleaf.pagination.v2.service.ResidentService;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

// Causes Lombok to generate a logger field.
@Slf4j
@Controller
public class ResidentController {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private ResidentService service;

    @Autowired
    private ResidentRepository repository;

    @GetMapping(value = "/")
    public String viewIndexPage() {
        log.info("Redirecting the index page to the controller method for fetching the residents in a paginated fashion.");
        return "redirect:residents/paginated/" + DEFAULT_PAGE_NUMBER + "/" + DEFAULT_PAGE_SIZE;
    }

    @GetMapping("/residents")
    public String getPaginatedResidents(Model model, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "next") String dir,
            @RequestParam(defaultValue = "0") Long page,
            @RequestParam(defaultValue = "10") Long size) {
        log.info("Getting the residents in a paginated way for page-number = {} and page-size = {}.", page, size);
        if (dir.equals("next")) {
            List<Resident> fetchAllAscNext = repository.fetchAllAscNext(page, size);
            model.addAttribute("responseEntity", fetchAllAscNext);
            model.addAttribute("max", fetchAllAscNext.get(fetchAllAscNext.size() - 1).getId());
            model.addAttribute("min", fetchAllAscNext.get(0).getId());
        } else {
            List<Resident> fetchAllAscPrevious = repository.fetchAllAscPrevious(page, size);
            Collections.reverse(fetchAllAscPrevious);
            model.addAttribute("responseEntity", fetchAllAscPrevious);
            model.addAttribute("max", fetchAllAscPrevious.get(fetchAllAscPrevious.size() - 1).getId());
            model.addAttribute("min", fetchAllAscPrevious.get(0).getId());
        }
        model.addAttribute("fetchCount", repository.fetchCount());
        model.addAttribute("fetchMinId", repository.fetchMinId());
        model.addAttribute("fetchMaxId", repository.fetchMaxId());
        return "index";
    }

}
