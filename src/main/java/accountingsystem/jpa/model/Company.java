package accountingsystem.jpa.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Company extends User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToOne
    private Person responsiblePerson;

    public Company() {
        super();
    }

    public Company(String email, String password, String name, Person responsiblePerson) {
        super();
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
