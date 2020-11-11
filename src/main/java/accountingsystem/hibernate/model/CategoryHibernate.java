package accountingsystem.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class CategoryHibernate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CategoryHibernate> subCategories;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Person> responsiblePeople;

    @OneToOne
    private CategoryHibernate parentCategoryHibernate;

    public CategoryHibernate() {

    }

    public CategoryHibernate(long id, String name, String description, List<Transaction> transactions, List<CategoryHibernate> subCategories, List<Person> responsiblePeople, CategoryHibernate parentCategoryHibernate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.transactions = transactions;
        this.subCategories = subCategories;
        this.responsiblePeople = responsiblePeople;
        this.parentCategoryHibernate = parentCategoryHibernate;
    }

    public CategoryHibernate(String name, String description, List<Transaction> transactions, List<CategoryHibernate> subCategories, List<Person> responsiblePeople, CategoryHibernate parentCategoryHibernate) {
        this.name = name;
        this.description = description;
        this.transactions = transactions;
        this.subCategories = subCategories;
        this.responsiblePeople = responsiblePeople;
        this.parentCategoryHibernate = parentCategoryHibernate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<CategoryHibernate> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryHibernate> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Person> getResponsiblePeople() {
        return responsiblePeople;
    }

    public void setResponsiblePeople(List<Person> responsiblePeople) {
        this.responsiblePeople = responsiblePeople;
    }

    public CategoryHibernate getParentCategory() {
        return parentCategoryHibernate;
    }

    public void setParentCategory(CategoryHibernate parentCategoryHibernate) {
        this.parentCategoryHibernate = parentCategoryHibernate;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void addResponsiblePerson(Person person) {
        this.responsiblePeople.add(person);
    }

}
