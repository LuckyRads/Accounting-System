package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CompanyUtil;
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
public class CompanyController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);

    @GetMapping(value = "company/companies")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllCompanies() throws JSONException {
        return JSONSerializer.serializeArray(companyUtil.getAllCompanies());
    }

    @GetMapping(value = "company/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getCompany(@PathVariable Long id) throws JSONException {
        return JSONSerializer.serializeObject(companyUtil.getCompany(id)).toString();
    }

    @PostMapping(value = "company/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createCompany(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String email = (String) data.get("email");
        String password = (String) data.get("password");
        String name = (String) data.get("name");
        String responsiblePersonEmail = (String) data.get("responsiblePerson");

        Person responsiblePerson = null;
        if (responsiblePersonEmail != null) {
            responsiblePerson = personUtil.getPerson(responsiblePersonEmail);
        }

        Company company = new Company(email, password, name, responsiblePerson);
        companyUtil.create(company);

        return "Success";
    }

    @DeleteMapping(value = "company/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCompany(@RequestBody String request) throws Exception {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        Long id = Long.parseLong((String) data.get("id"));
        String email = (String) data.get("email");

        if (id != null) {
            companyUtil.destroy(id);
        } else {
            companyUtil.destroy(email);
        }

        return "Success";
    }

}
