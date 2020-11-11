package accountingsystem.service;

import accountingsystem.model.Company;

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

    public static void addReplaceCompany(Company companyToAdd, List<Company> companies) {
        for (Company company : companies) {
            if (companyToAdd.getEmail().equals(company.getEmail())) {
                companies.remove(company);
                break;
            }
        }
        companies.add(companyToAdd);
    }

}
