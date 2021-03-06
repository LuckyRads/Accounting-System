package accountingsystem.hibernate.util;

import accountingsystem.hibernate.model.AccountingSystem;
import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.model.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CategoryUtil {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public CategoryUtil(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //region CRUD operations

    public void create(Category category) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(category));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(Category category) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            entityManager.merge(category);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void destroy(String name) throws Exception {
        EntityManager entityManager = null;

        try {
            for (Category category : getAllCategories()) {
                if (category.getName().equals(name)) {
                    entityManager = getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.remove(entityManager.merge(category));
                    entityManager.getTransaction().commit();
                }
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void destroy(Long id) throws Exception {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Category category = null;
            try {
                category = entityManager.getReference(Category.class, id);

                if (category.getParentCategory() != null) {
                    Category parentCategory = category.getParentCategory();
                    parentCategory.getSubCategories().remove(category);
                    entityManager.merge(parentCategory);
                }
                for (Category subCategory : category.getSubCategories()) {
                    destroy(subCategory.getId());
                }
                category.getSubCategories().clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
            entityManager.merge(category);
            entityManager.remove(category);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void destroy(Category cat) throws Exception {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Category category = entityManager.getReference(Category.class, cat.getId());

            for (Person p : category.getResponsiblePeople()) {
                p.getManagedCategories().remove(category);
                entityManager.merge(p);
            }
            category.getTransactions().clear();
            entityManager.merge(category);
            for (Category c : category.getSubCategories()) {
                destroy(c);
            }
            category.getSubCategories().clear(); entityManager.merge(category);

            entityManager.remove(entityManager.merge(category));
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void addSubcategory(Category parentCategory, Category subcategory) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            subcategory.setParentCategory(parentCategory);
            parentCategory.getSubCategories().add(subcategory);

            entityManager.persist(subcategory);
            entityManager.merge(parentCategory);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    /**
     * This function does not use hibernate because of unknown issue with responsible person deletion.
     *
     * @param categoryId
     * @param personId
     */
    public void removeResponsiblePerson(long categoryId, long personId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/accounting_system", "root", "root");

            String sql = "DELETE FROM category_person WHERE managedCategories_id = ? AND responsiblePeople_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setLong(2, personId);
            preparedStatement.execute();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region Getters

    public Category getCategory(String name) {
        for (Category category : getAllCategories()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public Category getCategory(Long id) {
        for (Category category : getAllCategories()) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    public List<Category> getAllCategories() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Category.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Category> getRootCategories() {
        List<Category> categories = getAllCategories();
        List<Category> rootCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getParentCategory() == null) {
                rootCategories.add(category);
            }
        }
        return rootCategories;
    }

    //endregion

}
