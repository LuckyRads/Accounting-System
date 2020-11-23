package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@RestController
public class PersonController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);

    @GetMapping(value = "person/people")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllPeople() throws JSONException {
        return JSONSerializer.serializeArray(personUtil.getAllPeople());
    }

    @PostMapping(value = "person/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createPerson(@RequestBody String request) throws JSONException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String email = (String) data.get("email");
        String password = (String) data.get("password");
        String name = (String) data.get("name");
        String surname = (String) data.get("surname");
        String phoneNumber = (String) data.get("phoneNumber");

        Person person = new Person(email, password, name, surname, phoneNumber);
        personUtil.create(person);

        return "Success";
    }

}