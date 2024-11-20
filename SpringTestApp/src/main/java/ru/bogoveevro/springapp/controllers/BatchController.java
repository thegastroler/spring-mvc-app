package ru.bogoveevro.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bogoveevro.springapp.dao.PersonDAO;

@Controller
@RequestMapping("/batch-update-test")
public class BatchController {
    private final PersonDAO personDAO;

    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String commonUpdate() {
        personDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String batchUpdate() {
        personDAO.testBatchUpdate();
        return "redirect:/people";
    }
}
