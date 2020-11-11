package accountingsystem.service;

import accountingsystem.model.Person;

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

    public static void addReplacePerson(Person personToAdd, List<Person> people) {
        for (Person person : people) {
            if (personToAdd.getEmail().equals(person.getEmail())) {
                people.remove(person);
                break;
            }
        }
        people.add(personToAdd);
    }

}
