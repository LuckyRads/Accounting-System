package main.java.accountingsystem.service;

import main.java.accountingsystem.model.Category;

import java.util.List;

public class CategoryService {

    public static Category getCategory(String name, List<Category> categories) {
        for (Category category : categories) {
            if (category.getName().equals(name))
                return category;
        }

        Category foundCategory = null;

        for (Category category : categories) {
            foundCategory = getCategory(name, category.getSubCategories());
        }

        return foundCategory;
    }

    public static void addReplaceCategory(Category categoryToAdd, List<Category> categories) {
        for (Category category : categories) {
            if (categoryToAdd.getName().equals(category.getName())) {
                categories.remove(category);
                break;
            }
        }
        categories.add(categoryToAdd);
    }

}
