package accountingsystem.rest.controller;

import accountingsystem.hibernate.util.AccountingSystemUtil;
import accountingsystem.service.JSONSerializer;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
public class AccountingSystemController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    AccountingSystemUtil accountingSystemUtil = new AccountingSystemUtil(entityManagerFactory);

    @GetMapping(value = "accounting-system")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAccountingSystem() throws JSONException {
        return JSONSerializer.serializeObject(accountingSystemUtil.getAccountingSystem());
    }

}
