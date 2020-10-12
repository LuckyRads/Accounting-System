package main.java.accountingsystem.service;

import main.java.accountingsystem.controller.CategoryController;
import main.java.accountingsystem.controller.CompanyController;
import main.java.accountingsystem.controller.PersonController;
import main.java.accountingsystem.model.*;

import java.io.*;
import java.util.List;

public class ImportService {

    public static void manageImports(AccountingSystem accountingSystem) {
        String message[] = {"Choose action",
                "all - import all available data",
                "cat - import all category data",
                "usr - user data import options",
                "sys - system data import options",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "all":
                    importAllData(accountingSystem);
                    break;
                case "cat":
                    importCategoryData(accountingSystem.getCategories());
                    break;
                case "usr":
                    manageUserImports(accountingSystem.getPeople(), accountingSystem.getCompanies());
                    break;
                case "sys":
                    importSystemData(accountingSystem);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void manageUserImports(List<Person> people, List<Company> companies) {
        String message[] = {"Choose action",
                "per - import people data",
                "com - import companies data",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "per":
                    importPeopleData(people);
                    break;
                case "com":
                    importCompaniesData(companies);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void importAllData(AccountingSystem accountingSystem) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allDataRead = false;
            while (!allDataRead) {
                try {
                    Object object = objectInputStream.readObject();

                    if (object instanceof AccountingSystem) {
                        AccountingSystem accountingSystemObject = (AccountingSystem) object;
                        accountingSystem.setCompany(accountingSystemObject.getCompany());
                        accountingSystem.setDateCreated(accountingSystemObject.getDateCreated());
                        accountingSystem.setVersion(accountingSystemObject.getVersion());
                    }
                    if (object instanceof Person) {
                        PersonController.addReplacePerson((Person) object,
                                accountingSystem.getPeople());
                    }
                    if (object instanceof Company) {
                        CompanyController.addReplaceCompany((Company) object,
                                accountingSystem.getCompanies());
                    }
                    if (object instanceof SubCategory) {
                        CategoryController.addReplaceCategory((SubCategory) object,
                                accountingSystem.getCategories());
                    }
                } catch (EOFException e) {
                    allDataRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importAllData(accountingSystem);
            return;
        }
    }

    public static void importCategoryData(List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allCategoriesRead = false;
            while (!allCategoriesRead) {
                try {
                    Object object = objectInputStream.readObject();
                    if (object instanceof SubCategory) {
                        CategoryController.addReplaceCategory((SubCategory) object,
                                categories);
                    }
                } catch (EOFException e) {
                    allCategoriesRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importCategoryData(categories);
            return;
        }
    }

    public static void importPeopleData(List<Person> people) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allPeopleRead = false;
            while (!allPeopleRead) {
                try {
                    Object object = objectInputStream.readObject();
                    if (object instanceof Person) {
                        PersonController.addReplacePerson((Person) object,
                                people);
                    }
                } catch (EOFException e) {
                    allPeopleRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importPeopleData(people);
            return;
        }
    }

    public static void importCompaniesData(List<Company> companies) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allCompaniesRead = false;
            while (!allCompaniesRead) {
                try {
                    Object object = objectInputStream.readObject();
                    if (object instanceof Company) {
                        CompanyController.addReplaceCompany((Company) object,
                                companies);
                    }
                } catch (EOFException e) {
                    allCompaniesRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importCompaniesData(companies);
            return;
        }
    }

    public static void importSystemData(AccountingSystem accountingSystem) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object object = objectInputStream.readObject();
            if (object instanceof AccountingSystem) {
                AccountingSystem accountingSystemObject = (AccountingSystem) object;
                accountingSystem.setCompany(accountingSystemObject.getCompany());
                accountingSystem.setDateCreated(accountingSystemObject.getDateCreated());
                accountingSystem.setVersion(accountingSystemObject.getVersion());
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importSystemData(accountingSystem);
            return;
        }
    }

}
