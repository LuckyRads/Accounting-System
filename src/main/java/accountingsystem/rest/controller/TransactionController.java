package accountingsystem.rest.controller;

import accountingsystem.hibernate.util.TransactionUtil;
import accountingsystem.service.JSONSerializer;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
public class TransactionController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    TransactionUtil transactionUtil = new TransactionUtil(entityManagerFactory);

    @GetMapping(value = "transaction/transactions")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllTransactions() throws JSONException {
        return JSONSerializer.serializeArray(transactionUtil.getAllTransactions());
    }

    @GetMapping(value = "transaction/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getTransaction(@PathVariable Long id) throws JSONException {
        return JSONSerializer.serializeObject(transactionUtil.getTransaction(id)).toString();
    }

}
