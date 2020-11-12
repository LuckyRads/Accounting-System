package accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExportController implements WindowController {

    @FXML
    private Button exportBtn;
//
//    @FXML
//    private ComboBox dataTypeSelect;
//
    @Override
    public void closeWindow() {
        Stage stage = (Stage) exportBtn.getScene().getWindow();
        stage.close();
    }
//
//    public void populateDataTypes() {
//        dataTypeSelect.getItems().clear();
//
//        dataTypeSelect.getItems().add("All data");
//        dataTypeSelect.getItems().add("System data");
//        dataTypeSelect.getItems().add("People data");
//        dataTypeSelect.getItems().add("Company data");
//        dataTypeSelect.getItems().add("Category data");
//
//        dataTypeSelect.getSelectionModel().select("All data");
//    }
//
//    @FXML
//    public void exportData() {
//        Stage stage = (Stage) exportBtn.getScene().getWindow();
//
//        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Serializable files (*.ser)", "*.ser");
//        fileChooser.getExtensionFilters().add(extensionFilter);
//        File file = fileChooser.showSaveDialog(stage);
//
//        if (file == null) {
//            return;
//        }
//
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//
//            objectOutputStream.writeObject(accountingSystem);
//
//            switch ((String) dataTypeSelect.getSelectionModel().getSelectedItem()) {
//                case "All data":
//                    exportAllData(objectOutputStream);
//                    break;
//                case "System data":
//                    exportSystemData(objectOutputStream);
//                    break;
//                case "People data":
//                    exportPeopleData(objectOutputStream);
//                    break;
//                case "Company data":
//                    exportCompanyData(objectOutputStream);
//                    break;
//                case "Category data":
//                    exportCategoryData(objectOutputStream);
//                    break;
//            }
//
//            fileOutputStream.close();
//            objectOutputStream.close();
//            closeWindow();
//        } catch (IOException e) {
//            System.out.println("Error! Something went wrong while exporting data." +
//                    "Please check if the destination path is correct");
//            return;
//        }
//    }
//
//    private void exportAllData(ObjectOutputStream objectOutputStream) throws IOException {
//        for (Person person : accountingSystem.getPeople()) {
//            objectOutputStream.writeObject(person);
//        }
//
//        for (Company company : accountingSystem.getCompanies()) {
//            objectOutputStream.writeObject(company);
//        }
//
//        for (Category category : accountingSystem.getCategories()) {
//            objectOutputStream.writeObject(category);
//        }
//    }
//
//    private void exportSystemData(ObjectOutputStream objectOutputStream) throws IOException {
//        objectOutputStream.writeObject(accountingSystem);
//    }
//
//    private void exportPeopleData(ObjectOutputStream objectOutputStream) throws IOException {
//        for (Person person : accountingSystem.getPeople()) {
//            objectOutputStream.writeObject(person);
//        }
//    }
//
//    private void exportCompanyData(ObjectOutputStream objectOutputStream) throws IOException {
//        for (Company company : accountingSystem.getCompanies()) {
//            objectOutputStream.writeObject(company);
//        }
//    }
//
//    private void exportCategoryData(ObjectOutputStream objectOutputStream) throws IOException {
//        for (Category category : accountingSystem.getCategories()) {
//            objectOutputStream.writeObject(category);
//        }
//    }

}
