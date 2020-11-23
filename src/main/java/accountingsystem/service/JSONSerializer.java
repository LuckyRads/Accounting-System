package accountingsystem.service;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONSerializer {

    public static <T> String serializeJSONArray(List<T> objects) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (T object : objects) {
            if (object instanceof Person) {
                jsonArray.put(serializeObject((Person) object));
            } else if (object instanceof Company) {
                jsonArray.put(serializeObject((Company) object));
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

}
