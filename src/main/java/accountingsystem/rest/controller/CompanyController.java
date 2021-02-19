package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@RestController
@RequestMapping("company")
public class CompanyController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);

    @GetMapping(value = "/companies")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllCompanies() {
        try {
            return JSONSerializer.serializeArray(companyUtil.getAllCompanies());
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getCompany(@PathVariable Long id) {
        try {
            return JSONSerializer.serializeObject(companyUtil.getCompany(id)).toString();
        } catch (Exception e) {
            return "Failed: such company does not exist.";
        }
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public String createCompany(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String email = (String) data.get("email");
            String password = (String) data.get("password");
            String name = (String) data.get("name");
            String responsiblePersonEmail = (String) data.get("responsiblePerson");

            if (email == null || password == null || name == null || responsiblePersonEmail == null) {
                return "Failed: one of the parameters is missing or incorrect.";
            }

            Person responsiblePerson = personUtil.getPerson(responsiblePersonEmail);

            Company company = new Company(email, password, name, responsiblePerson);
            companyUtil.create(company);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCompany(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String id = (String) data.get("id");

            if (id == null) {
                return "Failed: id not specified.";
            }

            try {
                companyUtil.destroy(Long.parseLong(id));
            } catch (Exception e) {
                return "Failed: such company does not exist.";
            }

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String editCompany(@RequestBody String request, @PathVariable Long id) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String email = (String) data.get("email");
            String password = (String) data.get("password");
            String name = (String) data.get("name");
            String responsiblePerson = (String) data.get("responsiblePerson");

            if (email == null && password == null && name == null && responsiblePerson == null) {
                return "Failed: no parameters were specified.";
            }

            Company company = companyUtil.getCompany(id);

            if (company == null) {
                return "Failed: such company does not exist.";
            }

            if (email != null) company.setEmail(email);
            if (password != null) company.setPassword(password);
            if (name != null) company.setName(name);
            if (responsiblePerson != null) company.setResponsiblePerson(personUtil.getPerson(responsiblePerson));

            companyUtil.edit(company);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

}
