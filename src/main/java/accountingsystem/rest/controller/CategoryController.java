package accountingsystem.rest.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.JSONSerializer;
import com.google.gson.Gson;
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
    public String getAllCategories() {
        try {
            return JSONSerializer.serializeArray(categoryUtil.getAllCategories());
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @GetMapping(value = "category/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getCategory(@PathVariable Long id) {
        try {
            return JSONSerializer.serializeObject(categoryUtil.getCategory(id)).toString();
        } catch (Exception e) {
            return "Failed: no such category found.";
        }
    }

    @PostMapping(value = "category/create")
    @ResponseStatus(value = HttpStatus.OK)
    public String createCategory(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String name = (String) data.get("name");
            String description = (String) data.get("description");

            String responsiblePeopleString = (String) data.get("responsiblePeople");
            List<Person> responsiblePeople = new ArrayList<>();
            Arrays.asList(responsiblePeopleString.split(",")).forEach(responsiblePerson -> {
                responsiblePeople.add(personUtil.getPerson(Long.parseLong(responsiblePerson)));
            });

            String parentCategoryId = (String) data.get("parentCategory");

            if (name == null && description == null) {
                return "Failed: missing name and description parameters.";
            }

            Category parentCategory = null;
            if (parentCategoryId != null) {
                parentCategory = categoryUtil.getCategory(Long.parseLong(parentCategoryId));
                if (parentCategory == null) {
                    return "Failed: no such parent category found with specified id.";
                }
            }

            Category category = new Category(name, description, new ArrayList<>(), new ArrayList<>(), responsiblePeople, parentCategory);
            categoryUtil.create(category);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @DeleteMapping(value = "category/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCategory(@RequestBody String request) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String id = (String) data.get("id");

            if (id == null) {
                return "Failed: id unspecified.";
            }

            try {
                categoryUtil.destroy(Long.parseLong(id));
            } catch (Exception e) {
                return "Failed: such category does not exist.";
            }

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

    @PostMapping(value = "category/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String editCategory(@RequestBody String request, @PathVariable Long id) {
        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);

            String name = (String) data.get("name");
            String description = (String) data.get("description");

            String responsiblePeopleString = (String) data.get("responsiblePeople");
            List<Person> responsiblePeople = new ArrayList<>();
            for (String responsiblePersonId : responsiblePeopleString.split(",")) {
                Person person = personUtil.getPerson(Long.parseLong(responsiblePersonId));
                if (person == null) {
                    return "Failed: one of the responsible people does not exist." +
                            "Please check if the specified id's are correct";
                }
                responsiblePeople.add(person);
            }

            String parentCategoryString = (String) data.get("parentCategory");

            Category category = categoryUtil.getCategory(id);

            if (category == null) {
                return "Failed: such category does not exist.";
            }

            Category parentCategory = category.getParentCategory();
            if (parentCategoryString != null) {
                parentCategory = categoryUtil.getCategory(Long.parseLong(parentCategoryString));
                if (parentCategory == null) {
                    return "Failed: parent category with such id does not exist.";
                }
            }

            category.getResponsiblePeople().forEach(responsiblePerson -> {
                categoryUtil.removeResponsiblePerson(category.getId(), responsiblePerson.getId());
            });

            if (name != null) category.setName(name);
            if (description != null) category.setDescription(description);
            if (responsiblePeople != null) category.setResponsiblePeople(responsiblePeople);
            if (parentCategory != null) category.setParentCategory(parentCategory);

            categoryUtil.edit(category);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: unexpected exception.";
        }
    }

}
