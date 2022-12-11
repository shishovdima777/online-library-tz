package ru.shishov.onlinelibrary.models;

import javax.validation.constraints.*;

public class Person {
    @NotEmpty(message = "Name shouldn't be empty")
    @Pattern(regexp = "[A-z\\s]*", message = "Name should contain only Latin letters")
    private String name;
    @NotNull(message = "Birth year shouldn't be empty")
    @Min(value = 1, message = "Birth year should be greater than 0")
    private int birthYear;
    private int person_id;

    public Person() {

    }

    public Person(int person_id, String name, int birthYear) {
        this.person_id = person_id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", person_id=" + person_id +
                '}';
    }
}
