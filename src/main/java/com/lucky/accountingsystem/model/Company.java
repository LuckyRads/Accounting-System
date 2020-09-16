package com.lucky.accountingsystem.model;

import java.io.Serializable;

public abstract class Company extends User implements Serializable {

    private String name;
    private Person responsiblePerson;

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
