package accountingsystem.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Category> subCategories;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<Person> responsiblePeople;

    @OneToOne(fetch = FetchType.EAGER)
    private Category parentCategory;

    public Category() {

    }

    public Category(long id, String name, String description, List<Transaction> transactions, List<Category> subCategories, List<Person> responsiblePeople, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.transactions = transactions;
        this.subCategories = subCategories;
        this.responsiblePeople = responsiblePeople;
        this.parentCategory = parentCategory;
    }

    public Category(String name, String description, List<Transaction> transactions, List<Category> subCategories, List<Person> responsiblePeople, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.transactions = transactions;
        this.subCategories = subCategories;
        this.responsiblePeople = responsiblePeople;
        this.parentCategory = parentCategory;
    }

    public long getId() {
        return id;
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

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Person> getResponsiblePeople() {
        return responsiblePeople;
    }

    public void setResponsiblePeople(List<Person> responsiblePeople) {
        this.responsiblePeople = responsiblePeople;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void addResponsiblePerson(Person person) {
        this.responsiblePeople.add(person);
    }

    public void removeResponsiblePerson(Person person) {
        this.responsiblePeople.remove(person);
    }

}
