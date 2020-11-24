package accountingsystem.hibernate.util;

import accountingsystem.hibernate.model.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class TransactionUtil {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public TransactionUtil(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //region CRUD operations

    public void create(Transaction transaction) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(transaction));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(Transaction transaction) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            transaction = entityManager.merge(transaction);
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
            for (Transaction transaction : getAllTransactions()) {
                if (transaction.getName().equals(name)) {
                    entityManager = getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.remove(entityManager.merge(transaction));
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
            Transaction transaction = null;
            try {
                transaction = entityManager.getReference(Transaction.class, id);
                transaction.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
            entityManager.remove(entityManager.merge(transaction));
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void destroy(Transaction transaction) throws Exception {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(transaction));
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    //endregion

    //region Getters

    public Transaction getTransaction(String name) {
        for (Transaction transaction : getAllTransactions()) {
            if (transaction.getName().equals(name)) {
                return transaction;
            }
        }
        return null;
    }

    public Transaction getTransaction(Long id) {
        for (Transaction transaction : getAllTransactions()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    public List<Transaction> getAllTransactions() {
        EntityManager entityManager = getEntityManager();

        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Transaction.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //endregion

}
