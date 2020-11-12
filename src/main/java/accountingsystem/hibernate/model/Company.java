package accountingsystem.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Company extends User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private Person responsiblePerson;

    public Company() {

    }

    public Company(String email, String password, String name, Person responsiblePerson) {
        super(email, password);
        this.name = name;
        this.responsiblePerson = responsiblePerson;
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return this.email;
    }
}
