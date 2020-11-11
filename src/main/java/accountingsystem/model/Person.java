package accountingsystem.model;

import java.io.Serializable;

public class Person extends User implements Serializable {

    private String name;
    private String surname;
    private String phoneNumber;

    public Person(String email, String password, String name, String surname, String phoneNumber) {
        super(email, password);
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return this.email;
    }
}
