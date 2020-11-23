package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@RestController
public class CategoryController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CategoryUtil categoryUtil = new CategoryUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);

    @GetMapping(value = "category/categories")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllCategories() throws JSONException {
        return JSONSerializer.serializeArray(categoryUtil.getAllCategories());
    }

    @GetMapping(value = "category/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getCategory(@PathVariable Long id) throws JSONException {
        return JSONSerializer.serializeObject(categoryUtil.getCategory(id)).toString();
    }

    @PostMapping(value = "category/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createCategory(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String name = (String) data.get("name");
        String description = (String) data.get("description");

        String responsiblePeopleString = (String) data.get("responsiblePeople");
        List<Person> responsiblePeople = new ArrayList<>();
        Arrays.asList(responsiblePeopleString.split(",")).forEach(responsiblePerson -> {
            responsiblePeople.add(personUtil.getPerson(Long.parseLong(responsiblePerson)));
        });

        Long parentCategoryId = Long.parseLong((String) data.get("parentCategory"));

        Category parentCategory = null;
        if (parentCategoryId != null) {
            parentCategory = categoryUtil.getCategory(parentCategoryId);
        }

        Category category = new Category(name, description, null, null, responsiblePeople, parentCategory);
        categoryUtil.create(category);

        return "Success";
    }

    @DeleteMapping(value = "category/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCategory(@RequestBody String request) throws Exception {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        Long id = Long.parseLong((String) data.get("id"));
        String name = (String) data.get("name");

        if (id != null) {
            categoryUtil.destroy(id);
        } else {
            categoryUtil.destroy(name);
        }

        return "Success";
    }

    @PostMapping(value = "category/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String editCategory(@RequestBody String request, @PathVariable Long id) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);

        String name = (String) data.get("name");
        String description = (String) data.get("description");

        String responsiblePeopleString = (String) data.get("responsiblePeople");
        List<Person> responsiblePeople = new ArrayList<>();
        Arrays.asList(responsiblePeopleString.split(",")).forEach(responsiblePerson -> {
            responsiblePeople.add(personUtil.getPerson(Long.parseLong(responsiblePerson)));
        });

        Long parentCategoryId = Long.parseLong((String) data.get("parentCategory"));

        Category parentCategory = null;
        if (parentCategoryId != null) {
            parentCategory = categoryUtil.getCategory(parentCategoryId);
        }

        Category category = categoryUtil.getCategory(id);

        category.setName(name);
        category.setDescription(description);
        category.setResponsiblePeople(responsiblePeople);
        category.setParentCategory(parentCategory);

        categoryUtil.edit(category);

        return "Success";
    }

}
