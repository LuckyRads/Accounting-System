package accountingsystem.hibernate.util;

import accountingsystem.hibernate.model.AccountingSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

public class AccountingSystemUtil {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public AccountingSystemUtil(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //region CRUD operations

    private void create(AccountingSystem accountingSystem) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(accountingSystem));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(AccountingSystem accountingSystem) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            entityManager.merge(accountingSystem);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    //endregion

    //region Getters

    public AccountingSystem getAccountingSystem() {
        EntityManager entityManager = getEntityManager();

        try {
            AccountingSystem accountingSystem = entityManager.find(AccountingSystem.class, (long) 1);

            if (accountingSystem == null) {
                create(getDefaultAccountingSystem());
                return getDefaultAccountingSystem();
            } else {
                return accountingSystem;
            }
        } finally {
            entityManager.close();
        }
    }


    private AccountingSystem getDefaultAccountingSystem() {
        return new AccountingSystem(
                (long) 1,
                "VGTU",
                LocalDate.of(2020, 10, 5),
                "1.0.0"
        );
    }

    //endregion

}
