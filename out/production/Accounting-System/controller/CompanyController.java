package main.java.accountingsystem.controller;

import main.java.accountingsystem.model.Company;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.service.InputService;
import main.java.accountingsystem.service.MessageService;

import java.util.List;

public class CompanyController {

    public static void manageCompanies(List<Company> companies, List<Person> people) {
        String message[] = {"Choose an action",
                "show - show companies",
                "cre - create company",
                "desc - describe company",
                "upd - update company",
                "rem - remove company",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "show":
                    showCompanies(companies);
                    break;
                case "cre":
                    companies.add(createCompany(people));
                    break;
                case "desc":
                    describeCompany(companies);
                    break;
                case "upd":
                    updateCompany(companies, people);
                    break;
                case "rem":
                    removeCompany(companies);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void showCompanies(List<Company> companies) {
        System.out.println("Companies:");
        for (Company company : companies) {
            System.out.println(company.getEmail());
        }
    }

    public static Company createCompany(List<Person> people) {
        System.out.println("Company creation");

        System.out.println("Enter email:");
        String email = InputService.getInput();

        System.out.println("Enter password:");
        String password = InputService.getInput();

        System.out.println("Enter name:");
        String name = InputService.getInput();

        System.out.println("Enter responsible person:");
        Person responsiblePerson = PersonController.choosePerson(people);

        return new Company(email, password, name, responsiblePerson);
    }

    public static void describeCompany(List<Company> companies) {
        MessageService.showMessage(new String[]{"Choose which company's info you would like to see:"});

        showCompanies(companies);

        String username = InputService.getInput();

        for (Company company : companies) {
            if (username.equals(company.getEmail())) {
                describeCompany(company);
            }
        }
    }

    public static void updateCompany(List<Company> companies, List<Person> people) {
        MessageService.showMessage(new String[]{"Choose which company's info you would like to update:"});

        showCompanies(companies);

        String username = InputService.getInput();

        for (Company company : companies) {
            if (username.equals(company.getEmail())) {
                companies.remove(company);
                company = updateCompany(company, people);
                companies.add(company);
            }
        }
    }

    public static void removeCompany(List<Company> companies) {
        MessageService.showMessage(new String[]{"Choose which company you would like to remove:"});

        showCompanies(companies);

        String username = InputService.getInput();

        for (Company company : companies) {
            if (username.equals(company.getEmail())) {
                companies.remove(company);
                return;
            }
        }
    }

    private static void describeCompany(Company company) {
        MessageService.showMessage(new String[]{"Company info",
                "email: " + company.getEmail(),
                "name: " + company.getName(),
                "responsible person's email: " + company.getResponsiblePerson().getEmail(),
                "responsible person's name: " + company.getResponsiblePerson().getName(),
                "responsible person's surname: " + company.getResponsiblePerson().getSurname(),
                "responsible person's phone number: " + company.getResponsiblePerson().getPhoneNumber()});
    }

    private static Company updateCompany(Company company, List<Person> people) {
        MessageService.showMessage(new String[]{"Edit company's info",
                "Enter new company's info in format:",
                "email;password;name"});

        String personInfo[] = InputService.getInput().split(";");

        Person responsiblePerson = PersonController.choosePerson(people);

        return new Company(personInfo[0], personInfo[1], personInfo[2], responsiblePerson);
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
