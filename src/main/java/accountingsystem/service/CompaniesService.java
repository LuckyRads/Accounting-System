package main.java.accountingsystem.service;

import main.java.accountingsystem.model.Company;

import java.util.List;

public class CompaniesService {

    public static Company getCompany(String email, List<Company> companies) {
        for (Company company : companies) {
            if (email.equals(company.getEmail())) {
                return company;
            }
        }
        return null;
    }

}
