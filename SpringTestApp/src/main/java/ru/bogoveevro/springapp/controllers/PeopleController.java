package ru.bogoveevro.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bogoveevro.springapp.dao.PersonDAO;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("people", personDAO.getAll());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable int id, Model model) {
        model.addAttribute("person", personDAO.getOne(id));
        return "/people/show";
    }
}
