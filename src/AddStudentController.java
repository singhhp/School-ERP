// AddStudentController.java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField classField;

    @FXML
    private TextField phoneField;

    private StudentDAO studentDAO;

    private AdminDashboardController adminDashboardController;

    // Setter for StudentDAO
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // Setter for AdminDashboardController to refresh data after adding
    public void setAdminDashboardController(AdminDashboardController controller) {
        this.adminDashboardController = controller;
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        String name = nameField.getText().trim();
        String className = classField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        if (name.isEmpty() || className.isEmpty() || phoneNumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        // Optionally, add validation for phone number format

        Student newStudent = new Student(0, name, className, phoneNumber);
        studentDAO.addStudent(newStudent);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully.");

        // Close the window
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
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
