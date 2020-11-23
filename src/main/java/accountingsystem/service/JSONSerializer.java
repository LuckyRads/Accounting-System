package accountingsystem.service;

import accountingsystem.hibernate.model.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONSerializer {

    public static String serializeJSONArray(List<Person> people) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Person person : people) {
            jsonArray.put(serializePerson(person));
        }

        return jsonArray.toString(2);
    }

    private static JSONObject serializePerson(Person person) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", person.getId());
        object.put("name", person.getName());
        object.put("surname", person.getSurname());
        object.put("phoneNumber", person.getPhoneNumber());
        object.put("managedCategories", new JSONArray(person.getManagedCategories().toString()));

        return object;
    }

}
