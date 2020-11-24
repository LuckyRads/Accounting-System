package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.AccountingSystem;
import accountingsystem.hibernate.util.AccountingSystemUtil;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
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
    public String getAccountingSystem() {
        try {
            return JSONSerializer.serializeObject(accountingSystemUtil.getAccountingSystem());
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @PostMapping(value = "accounting-system")
    @ResponseStatus(value = HttpStatus.OK)
    public String editAccountingSystem(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String company = (String) data.get("company");
            String dateCreated = (String) data.get("dateCreated");
            String version = (String) data.get("version");

            if (company == null && dateCreated == null && version == null) {
                return "Failed: no parameters were specified.";
            }

            AccountingSystem accountingSystem = accountingSystemUtil.getAccountingSystem();

            if (company != null) accountingSystem.setCompany(company);
            if (dateCreated != null) accountingSystem.setDateCreated(LocalDate.parse(dateCreated));
            if (version != null) accountingSystem.setVersion(version);

            accountingSystemUtil.edit(accountingSystem);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: Unexpected exception.";
        }
    }

}
