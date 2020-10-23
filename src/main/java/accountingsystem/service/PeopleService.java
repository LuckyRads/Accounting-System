package main.java.accountingsystem.service;

import main.java.accountingsystem.model.Person;

import java.util.List;

public class PeopleService {

    public static Person getPerson(String email, List<Person> people) {
        for (Person person : people) {
            if (email.equals(person.getEmail())) {
                return person;
            }
        }
        return null;
    }

}
