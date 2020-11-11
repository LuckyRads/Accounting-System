package accountingsystem.hibernate.util;

import accountingsystem.hibernate.model.AccountingSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Calendar;

public class AccountingSystemUtil {

    private EntityManagerFactory entityManagerFactory = null;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public AccountingSystemUtil(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void edit(AccountingSystem accountingSystem) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            accountingSystem = entityManager.merge(accountingSystem);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

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

    private AccountingSystem getDefaultAccountingSystem() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 5);

        return new AccountingSystem(
                (long) 1,
                "VGTU",
                calendar.getTime(),
                "1.0.0"
        );
    }

}
