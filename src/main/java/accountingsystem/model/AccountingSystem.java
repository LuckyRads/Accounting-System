package main.java.accountingsystem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AccountingSystem implements Serializable {

    private String company;
    private Date dateCreated;
    private String version;
    private List<SubCategory> categories;
    private List<Person> people;
    private List<Company> companies;

    public AccountingSystem(String company, Date dateCreated, String version, List<SubCategory> categories, List<Person> people, List<Company> companies) {
        this.company = company;
        this.dateCreated = dateCreated;
        this.version = version;
        this.categories = categories;
        this.people = people;
        this.companies = companies;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<SubCategory> getCategories() {
        return this.categories;
    }

    public void setCategories(List<SubCategory> categories) {
        this.categories = categories;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

}
