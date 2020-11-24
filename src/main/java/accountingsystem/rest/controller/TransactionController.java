package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Transaction;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.TransactionUtil;
import accountingsystem.model.TransactionType;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
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
    public String getAllTransactions() {
        try {
            return JSONSerializer.serializeArray(transactionUtil.getAllTransactions());
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @GetMapping(value = "transaction/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getTransaction(@PathVariable Long id) {
        try {
            return JSONSerializer.serializeObject(transactionUtil.getTransaction(id)).toString();
        } catch (Exception e) {
            return "Failed: no such transaction found.";
        }
    }

    @PostMapping(value = "transaction/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createTransaction(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String name = (String) data.get("name");
            String transactionTypeString = (String) data.get("transactionType");
            String sender = (String) data.get("sender");
            String receiver = (String) data.get("receiver");
            String amount = (String) data.get("amount");
            String date = (String) data.get("date");
            String categoryId = (String) data.get("category");

            if (name == null || transactionTypeString == null || sender == null || receiver == null || amount == null ||
                    date == null || categoryId == null) {
                return "Failed: one of the parameters is wrong or missing.";
            }

            TransactionType transactionType = TransactionType.INCOME;
            if (transactionTypeString.equalsIgnoreCase("EXPENSE")) {
                transactionType = TransactionType.EXPENSE;
            }

            Category category = categoryUtil.getCategory(Long.parseLong(categoryId));
            if (category == null) {
                return "Failed: no such category found with the specified id.";
            }

            Transaction transaction =
                    new Transaction(name, transactionType, sender, receiver, Double.parseDouble(amount), LocalDate.parse(date), category);

            transactionUtil.create(transaction);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @DeleteMapping(value = "transaction/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteTransaction(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String id = (String) data.get("id");

            try {
                transactionUtil.destroy(Long.parseLong(id));
            } catch (Exception e) {
                return "Failed: no such transaction exist.";
            }

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @PostMapping(value = "transaction/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String editTransaction(@RequestBody String request, @PathVariable Long id) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String name = (String) data.get("name");
            String transactionTypeString = (String) data.get("transactionType");
            String sender = (String) data.get("sender");
            String receiver = (String) data.get("receiver");
            String amount = (String) data.get("amount");
            String date = (String) data.get("date");
            String categoryId = (String) data.get("category");

            TransactionType transactionType = TransactionType.INCOME;
            if (transactionTypeString.equalsIgnoreCase("EXPENSE")) {
                transactionType = TransactionType.EXPENSE;
            }

            Category category = null;
            if (categoryId != null) {
                category = categoryUtil.getCategory(Long.parseLong(categoryId));
                if (category == null) {
                    return "Failed: no such category exists with the specified id.";
                }
            }

            Transaction transaction = transactionUtil.getTransaction(id);

            if (transaction == null) {
                return "Failed: such transaction does not exist.";
            }

            if (name != null) transaction.setName(name);
            if (transactionType != null) transaction.setTransactionType(transactionType);
            if (sender != null) transaction.setSender(sender);
            if (receiver != null) transaction.setReceiver(receiver);
            if (amount != null) transaction.setAmount(Double.parseDouble(amount));
            if (data != null) transaction.setDate(LocalDate.parse(date));
            if (category != null) transaction.setCategory(category);

            transactionUtil.edit(transaction);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

}
