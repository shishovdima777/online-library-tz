package ru.shishov.onlinelibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shishov.onlinelibrary.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birth_year) VALUES (?, ?)", person.getName(), person.getBirthYear());
    }
    public Person getPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }
    public Optional<Person> getPerson(String name) {
       return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new BeanPropertyRowMapper<>(Person.class), new Object[] {name}).stream().findAny();
    }
    public void update(Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET name=?, birth_year=? WHERE person_id=?", person.getName(), person.getBirthYear(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}
