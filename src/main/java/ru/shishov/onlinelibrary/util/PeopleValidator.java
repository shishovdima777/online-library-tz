package ru.shishov.onlinelibrary.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shishov.onlinelibrary.dao.PeopleDAO;
import ru.shishov.onlinelibrary.models.Person;
@Component
public class PeopleValidator implements Validator {
    private final PeopleDAO peopleDAO;

    public PeopleValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(peopleDAO.getPerson(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken.");
        }
    }
}
