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

    @GetMapping(value = "person/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getPerson(@PathVariable Long id) throws JSONException {
        return JSONSerializer.serializeObject(personUtil.getPerson(id)).toString();
    }

    @PostMapping(value = "person/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createPerson(@RequestBody String request) {
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

    @DeleteMapping(value = "person/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public String deletePerson(@RequestBody String request) throws Exception {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        Long id = Long.parseLong((String) data.get("id"));
        String email = (String) data.get("email");

        if (id != null) {
            personUtil.destroy(id);
        } else {
            personUtil.destroy(email);
        }

        return "Success";
    }

    @PostMapping(value = "person/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String editPerson(@RequestBody String request, @PathVariable Long id) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String email = (String) data.get("email");
        String password = (String) data.get("password");
        String name = (String) data.get("name");
        String surname = (String) data.get("surname");
        String phoneNumber = (String) data.get("phoneNumber");

        Person person = personUtil.getPerson(id);

        person.setEmail(email);
        person.setPassword(password);
        person.setName(name);
        person.setSurname(surname);
        person.setPhoneNumber(phoneNumber);

        personUtil.edit(person);

        return "Success";
    }

}
