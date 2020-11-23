package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Transaction;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.TransactionUtil;
import accountingsystem.model.TransactionType;
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
public class TransactionController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    TransactionUtil transactionUtil = new TransactionUtil(entityManagerFactory);
    CategoryUtil categoryUtil = new CategoryUtil(entityManagerFactory);

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

    @PostMapping(value = "transaction/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createTransaction(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String name = (String) data.get("name");

        String transactionTypeString = (String) data.get("transactionType");
        TransactionType transactionType = TransactionType.INCOME;
        if (transactionTypeString.equalsIgnoreCase("EXPENSE")) {
            transactionType = TransactionType.EXPENSE;
        }

        String sender = (String) data.get("sender");
        String receiver = (String) data.get("receiver");
        double amount = Double.parseDouble((String) data.get("amount"));
        LocalDate date = LocalDate.parse((String) data.get("date"));
        Long categoryId = Long.parseLong((String) data.get("category"));

        Category category = null;
        if (categoryId != null) {
            category = categoryUtil.getCategory(categoryId);
        }

        Transaction transaction = new Transaction(name, transactionType, sender, receiver, amount, date, category);
        transactionUtil.create(transaction);

        return "Success";
    }

}
