package ru.bogoveevro.springapp.dao;

import org.springframework.stereotype.Component;
import ru.bogoveevro.springapp.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int peopleCount;
    private final List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++peopleCount, "John"));
        people.add(new Person(++peopleCount, "Mike"));
        people.add(new Person(++peopleCount, "Adam"));
        people.add(new Person(++peopleCount, "Richard"));
    }

    public List<Person> getAll() {
        return people;
    }

    public Person getOne(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}
