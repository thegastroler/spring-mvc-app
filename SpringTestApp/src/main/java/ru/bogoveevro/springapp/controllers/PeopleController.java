package ru.bogoveevro.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bogoveevro.springapp.dao.PersonDAO;
import ru.bogoveevro.springapp.models.Person;
import ru.bogoveevro.springapp.validators.PersonValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("people", personDAO.getAll());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable int id, Model model) {
        Optional<Person> person = personDAO.getOne(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            return "people/person";
        } else {
            return "404";
        }
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable int id, Model model) {
        Optional<Person> person = personDAO.getOne(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            return "people/edit";
        } else {
            return "404";
        }
    }

    @PatchMapping("/{id}")
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }
        personDAO.update(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
