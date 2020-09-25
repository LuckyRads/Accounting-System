package com.lucky.accountingsystem.controller;

import com.lucky.accountingsystem.model.Person;
import com.lucky.accountingsystem.model.User;
import com.lucky.accountingsystem.service.InputService;
import com.lucky.accountingsystem.service.MessageService;

import java.util.List;

public class PersonController {

    public static void managePeople(List<Person> people) {
        String message[] = {"Choose an action",
                "show - show people",
                "cre - create person",
                "desc - describe person",
                "upd - update person",
                "rem - remove person",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "show":
                    showPeople(people);
                    break;
                case "cre":
                    people.add(createPerson());
                    break;
                case "desc":
                    describePerson(people);
                    break;
                case "upd":
                    updatePerson(people);
                    break;
                case "rem":
                    removePerson(people);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void showPeople(List<Person> people) {
        System.out.println("People:");
        for (User person : people) {
            System.out.println(person.getEmail());
        }
    }

    public static Person createPerson() {
        System.out.println("Person creation");

        System.out.println("Enter email:");
        String email = InputService.getInput();

        System.out.println("Enter password:");
        String password = InputService.getInput();

        System.out.println("Enter name:");
        String name = InputService.getInput();

        System.out.println("Enter surname:");
        String surname = InputService.getInput();

        System.out.println("Enter phone number:");
        String phoneNumber = InputService.getInput();

        return new Person(email, password, name, surname, phoneNumber);
    }

    public static void describePerson(List<Person> people) {
        MessageService.showMessage(new String[]{"Choose which person's info you would like to see:"});

        showPeople(people);

        String username = InputService.getInput();

        for (Person person : people) {
            if (username.equals(person.getEmail())) {
                describePerson(person);
            }
        }
    }

    public static void updatePerson(List<Person> people) {
        MessageService.showMessage(new String[]{"Choose which person's info you would like to update:"});

        showPeople(people);

        String username = InputService.getInput();

        for (Person person : people) {
            if (username.equals(person.getEmail())) {
                people.remove(person);
                person = updatePerson();
                people.add(person);
            }
        }
    }

    public static void removePerson(List<Person> people) {
        MessageService.showMessage(new String[]{"Choose which person you would like to remove:"});

        showPeople(people);

        String username = InputService.getInput();

        for (Person person : people) {
            if (username.equals(person.getEmail())) {
                people.remove(person);
                return;
            }
        }
    }

    private static void describePerson(Person person) {
        MessageService.showMessage(new String[]{"Person info",
                "email: " + person.getEmail(),
                "name: " + person.getName(),
                "surname: " + person.getSurname(),
                "phone number: " + person.getPhoneNumber()});
    }

    private static Person updatePerson() {
        MessageService.showMessage(new String[]{"Edit person's info",
                "Enter new person's info in format:",
                "email;password;name;surname;phone_number"});

        String personInfo[] = InputService.getInput().split(";");

        return new Person(personInfo[0], personInfo[1], personInfo[2], personInfo[3], personInfo[4]);
    }

    public static Person choosePerson(List<Person> people) {
        Person chosenPerson = null;
        MessageService.showMessage(new String[]{"Choose a person:"});

        showPeople(people);

        String username = InputService.getInput();

        for (Person person : people) {
            if (username.equals(person.getEmail())) {
                chosenPerson = person;
            }
        }
        return chosenPerson;
    }

}
