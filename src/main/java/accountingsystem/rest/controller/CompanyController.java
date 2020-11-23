package accountingsystem.rest.controller;

import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.service.JSONSerializer;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
public class CompanyController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);

    @GetMapping(value = "company/companies")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllCompanies() throws JSONException {
        return JSONSerializer.serializeArray(companyUtil.getAllCompanies());
    }

}
