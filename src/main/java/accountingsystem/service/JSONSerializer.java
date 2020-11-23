package accountingsystem.service;

import accountingsystem.hibernate.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONSerializer {

    public static <T> String serializeArray(List<T> objects) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (T object : objects) {
            if (object instanceof Person) {
                jsonArray.put(serializeObject((Person) object));
            } else if (object instanceof Company) {
                jsonArray.put(serializeObject((Company) object));
            } else if (object instanceof Category) {
                jsonArray.put(serializeObject((Category) object));
            } else if (object instanceof Transaction) {
                jsonArray.put(serializeObject((Transaction) object));
            }
        }

        return jsonArray.toString(2);
    }

    public static JSONObject serializeObject(Person person) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", person.getId());
        object.put("name", person.getName());
        object.put("surname", person.getSurname());
        object.put("phoneNumber", person.getPhoneNumber());
        object.put("managedCategories", new JSONArray(person.getManagedCategories().toString()));

        return object;
    }

    public static JSONObject serializeObject(Company company) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", company.getId());
        object.put("name", company.getName());
        object.put("managedCategories", company.getResponsiblePerson().toString());

        return object;
    }

    public static String serializeObject(AccountingSystem accountingSystem) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", accountingSystem.getId());
        object.put("company", accountingSystem.getCompany());
        object.put("dateCreated", accountingSystem.getDateCreated());
        object.put("version", accountingSystem.getVersion());

        return object.toString();
    }

    public static JSONObject serializeObject(Category category) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", category.getId());
        object.put("name", category.getName());
        object.put("description", category.getDescription());
        object.put("transactions", new JSONArray(category.getTransactions().toString()));
        object.put("subCategories", new JSONArray(category.getSubCategories().toString()));
        object.put("responsiblePeople", new JSONArray(category.getResponsiblePeople().toString()));
        if (category.getParentCategory() != null) object.put("parentCategory", category.getParentCategory().toString());

        return object;
    }

    public static JSONObject serializeObject(Transaction transaction) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", transaction.getId());
        object.put("name", transaction.getName());
        object.put("transactionType", transaction.getTransactionType());
        object.put("sender", transaction.getSender());
        object.put("receiver", transaction.getReceiver());
        object.put("amount", transaction.getAmount());
        object.put("date", transaction.getDate());
        object.put("category", transaction.getCategory().toString());

        return object;
    }

}
