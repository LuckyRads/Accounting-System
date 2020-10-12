package main.java.accountingsystem.model;

import java.io.Serializable;

public class Company extends User implements Serializable {

    private String name;
    private Person responsiblePerson;

    public Company(String email, String password, String name, Person responsiblePerson) {
        super(email, password);
        this.name = name;
        this.responsiblePerson = responsiblePerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

}
