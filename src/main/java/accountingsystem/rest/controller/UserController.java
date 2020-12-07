package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.hibernate.util.PersonUtil;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@RestController
public class UserController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);

    @PostMapping(value = "user/validate")
    @ResponseStatus(value = HttpStatus.OK)
    public String validateUser(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String username = (String) data.get("username");
            String password = (String) data.get("password");

            if (username == null || password == null) {
                return "Failed: username or password is missing.";
            }

            Person person = personUtil.getPerson(username);
            if (person != null) {
                return person.getPassword().equals(password) ? "person" : "Failed: authentication failed.";
            }
            Company company = companyUtil.getCompany(username);
            if (company != null) {
                return company.getPassword().equals(password) ? "company" : "Failed: authentication failed.";
            }
            return "Failed: authentication failed.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

}
