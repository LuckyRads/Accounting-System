package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
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
    public String getAllPeople() {
        try {
            return JSONSerializer.serializeArray(personUtil.getAllPeople());
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @GetMapping(value = "person/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getPerson(@PathVariable Long id) {
        try {
            return JSONSerializer.serializeObject(personUtil.getPerson(id)).toString();
        } catch (Exception e) {
            return "Failed: this person does not exist.";
        }
    }

    @PostMapping(value = "person/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createPerson(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String email = (String) data.get("email");
            String password = (String) data.get("password");
            String name = (String) data.get("name");
            String surname = (String) data.get("surname");
            String phoneNumber = (String) data.get("phoneNumber");

            if (email == null || password == null || name == null || surname == null || phoneNumber == null) {
                return "Failed: one of the parameters is missing or incorrect.";
            }

            Person person = new Person(email, password, name, surname, phoneNumber);
            personUtil.create(person);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @DeleteMapping(value = "person/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public String deletePerson(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String id = (String) data.get("id");

            if (id == null) {
                return "Failed: id not specified.";
            }

            try {
                personUtil.destroy(Long.parseLong(id));
            } catch (Exception e) {
                return "Failed: person does not exist.";
            }

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @PostMapping(value = "person/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String editPerson(@RequestBody String request, @PathVariable Long id) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String email = (String) data.get("email");
            String password = (String) data.get("password");
            String name = (String) data.get("name");
            String surname = (String) data.get("surname");
            String phoneNumber = (String) data.get("phoneNumber");

            if (email == null && password == null && name == null && surname == null && phoneNumber == null) {
                return "Failed: no parameters were specified.";
            }

            Person person = personUtil.getPerson(id);

            if (person == null) {
                return "Failed: such person does not exist.";
            }

            if (email != null) person.setEmail(email);
            if (password != null) person.setPassword(password);
            if (name != null) person.setName(name);
            if (surname != null) person.setSurname(surname);
            if (phoneNumber != null) person.setPhoneNumber(phoneNumber);

            personUtil.edit(person);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

}
