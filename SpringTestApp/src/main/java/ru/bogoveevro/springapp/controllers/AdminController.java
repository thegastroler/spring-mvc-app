package ru.bogoveevro.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bogoveevro.springapp.dao.PersonDAO;
import ru.bogoveevro.springapp.models.Person;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonDAO personDAO;

    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDAO.getAll());
        return "admin/index";
    }

    @PatchMapping()
    public String addAdmin(@ModelAttribute("person") Person person) {
        System.out.println(person.getId());
        return "redirect:/people";
    }
}
