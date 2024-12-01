package ru.bogoveevro.springapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bogoveevro.springapp.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person", Person.class).getResultList();
    }

    public Optional<Person> getOne(String email) {
        return null;
    }

    public Optional<Person> getOne(int id) {
        return null;
    }

    public void save(Person person) {
    }

    public void update(Person updatedPerson, int id) {
    }

    public void delete(int id) {
    }

    public void testMultipleUpdate() {
    }

    public void testBatchUpdate() {
    }
}
