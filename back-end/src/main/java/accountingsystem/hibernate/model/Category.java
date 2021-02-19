package accountingsystem.hibernate.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
    private List<Transaction> transactions;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parentCategory")
    private List<Category> subCategories;

    @ManyToMany(cascade = CascadeType.MERGE)
    @OrderBy("id asc")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> responsiblePeople;

    @ManyToOne
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

}
