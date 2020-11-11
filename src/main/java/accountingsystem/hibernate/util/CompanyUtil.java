package accountingsystem.hibernate.util;

import accountingsystem.hibernate.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class CompanyUtil {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public CompanyUtil(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //region CRUD operations

    public void create(Company company) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(company));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(Company company) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            company = entityManager.merge(company);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void destroy(String email) throws Exception {
        EntityManager entityManager = null;

        try {
            for (Company company : getAllCompanies()) {
                if (company.getEmail().equals(email)) {
                    entityManager = getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.remove(company);
                    entityManager.getTransaction().commit();
                }
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void destroy(Company company) throws Exception {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(company));
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    //endregion

    //region Getters

    public Company getCompany(String email) {
        for (Company company : getAllCompanies()) {
            if (company.getEmail().equals(email)) {
                return company;
            }
        }
        return null;
    }

    public List<Company> getAllCompanies() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Company.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //endregion

}
