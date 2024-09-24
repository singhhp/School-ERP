// AdminDashboardController.java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminDashboardController {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> classColumn;

    @FXML
    private TableColumn<Student, String> phoneColumn;

    private StudentDAO studentDAO = new StudentDAO();

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Load student data
        loadStudentData();
    }

    private void loadStudentData() {
        studentList.clear();
        List<Student> students = studentDAO.getAllStudents();
        studentList.addAll(students);
        studentTable.setItems(studentList);
    }

    @FXML
    private void handleAddStudent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudent.fxml"));
            Parent root = loader.load();

            // Pass the studentDAO to the AddStudentController
            AddStudentController controller = loader.getController();
            controller.setStudentDAO(studentDAO);
            controller.setAdminDashboardController(this);

            Stage stage = new Stage();
            stage.setTitle("Add Student");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after adding
            loadStudentData();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load Add Student window.");
        }
    }

    @FXML
    private void handleUpdateStudent(ActionEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateStudent.fxml"));
            Parent root = loader.load();

            // Pass the selected student and studentDAO to the UpdateStudentController
            UpdateStudentController controller = loader.getController();
            controller.setStudentDAO(studentDAO);
            controller.setAdminDashboardController(this);
            controller.setStudent(selectedStudent);

            Stage stage = new Stage();
            stage.setTitle("Update Student");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after updating
            loadStudentData();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load Update Student window.");
        }
    }

    @FXML
    private void handleDeleteStudent(ActionEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete student: " + selectedStudent.getName() + "?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                studentDAO.deleteStudent(selectedStudent.getId());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully.");
                loadStudentData();
            }
        });
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadStudentData();
    }

    // Utility method for showing alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
