package accountingsystem.rest.controller;

import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.service.JSONSerializer;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
public class CategoryController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CategoryUtil categoryUtil = new CategoryUtil(entityManagerFactory);

    @GetMapping(value = "category/categories")
    @ResponseStatus(value = HttpStatus.OK)
    public String getAllCategories() throws JSONException {
        return JSONSerializer.serializeArray(categoryUtil.getAllCategories());
    }

}