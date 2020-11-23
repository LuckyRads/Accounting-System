package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.AccountingSystem;
import accountingsystem.hibernate.util.AccountingSystemUtil;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Properties;

@RestController
public class AccountingSystemController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    AccountingSystemUtil accountingSystemUtil = new AccountingSystemUtil(entityManagerFactory);

    @GetMapping(value = "accounting-system")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAccountingSystem() throws JSONException {
        return JSONSerializer.serializeObject(accountingSystemUtil.getAccountingSystem());
    }

    @PostMapping(value = "accounting-system")
    @ResponseStatus(value = HttpStatus.OK)
    public String editAccountingSystem(@RequestBody String request) throws JSONException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String company = (String) data.get("company");
        String dateCreated = (String) data.get("dateCreated");
        String version = (String) data.get("version");

        LocalDate date = LocalDate.parse(dateCreated);

        AccountingSystem accountingSystem = accountingSystemUtil.getAccountingSystem();

        accountingSystem.setCompany(company);
        accountingSystem.setDateCreated(date);
        accountingSystem.setVersion(version);

        accountingSystemUtil.edit(accountingSystem);

        return "Success";
    }

}
