package ru.bogoveevro.springapp.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bogoveevro.springapp.dao.PersonDAO;
import ru.bogoveevro.springapp.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> optionalPerson = personDAO.getOne(person.getEmail());

        if (optionalPerson.isPresent()) {
            Person findPerson = optionalPerson.get();
            if (findPerson.getId() != person.getId()) {
                errors.rejectValue("email", "", "This email is already taken");
            }
        }
    }
}
