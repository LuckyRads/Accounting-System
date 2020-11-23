package accountingsystem.rest.controller;

import accountingsystem.hibernate.util.PersonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
public class PersonController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);

    @RequestMapping(value = "person/people")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllPeople() {
        return "responseaaaaa";
    }

}
