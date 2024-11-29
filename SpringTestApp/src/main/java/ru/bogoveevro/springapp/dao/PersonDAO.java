package ru.bogoveevro.springapp.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.bogoveevro.springapp.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getOne(String email) {
        return jdbcTemplate.query("select * from person where email=?",
                new BeanPropertyRowMapper<>(Person.class), email).stream().findAny();
    }

    public Optional<Person> getOne(int id) {
        return jdbcTemplate.query("select * from person where id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, age, email, address) values (?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(Person updatedPerson, int id) {
        jdbcTemplate.update("update person set name=?, age=?, email=?, address=? where id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(),
                updatedPerson.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public void testMultipleUpdate() {
        List<Person> personList = get1000Persons();
        long start = System.currentTimeMillis();
        for (Person person : personList) {
            jdbcTemplate.update("insert into person (name, age, email) values (?, ?, ?)",
                    person.getName(), person.getAge(), person.getEmail());
        }
        long finish = System.currentTimeMillis();
        System.out.println((finish - start) + " millis");
    }

    public void testBatchUpdate() {
        List<Person> personList = get1000Persons();
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("insert into person (name, age, email) values (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, personList.get(i).getName());
                preparedStatement.setInt(2, personList.get(i).getAge());
                preparedStatement.setString(3, personList.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return personList.size();
            }
        });
        long finish = System.currentTimeMillis();
        System.out.println((finish - start) + " millis");
    }

    private List<Person> get1000Persons() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            personList.add(new Person(i, "Name " + i, i, i + "email@a.b", "a"));
        }
        return personList;
    }
}
