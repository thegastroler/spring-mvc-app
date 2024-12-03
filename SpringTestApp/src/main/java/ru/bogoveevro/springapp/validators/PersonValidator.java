package ru.bogoveevro.springapp.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bogoveevro.springapp.models.Person;
import ru.bogoveevro.springapp.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> optionalPerson = peopleService.findOne(person.getEmail());

        if (optionalPerson.isPresent()) {
            Person findPerson = optionalPerson.get();
            if (findPerson.getId() != person.getId()) {
                errors.rejectValue("email", "", "This email is already taken");
            }
        }
    }
}
