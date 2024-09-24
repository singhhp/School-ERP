// UpdateStudentController.java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateStudentController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField classField;

    @FXML
    private TextField phoneField;

    private StudentDAO studentDAO;

    private AdminDashboardController adminDashboardController;

    private Student student;

    // Setter for StudentDAO
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // Setter for AdminDashboardController to refresh data after updating
    public void setAdminDashboardController(AdminDashboardController controller) {
        this.adminDashboardController = controller;
    }

    // Setter for the selected student
    public void setStudent(Student student) {
        this.student = student;
        populateFields();
    }

    private void populateFields() {
        idField.setText(String.valueOf(student.getId()));
        nameField.setText(student.getName());
        classField.setText(student.getClassName());
        phoneField.setText(student.getPhoneNumber());
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String name = nameField.getText().trim();
        String className = classField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        if (name.isEmpty() || className.isEmpty() || phoneNumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        // Optionally, add validation for phone number format

        student.setName(name);
        student.setClassName(className);
        student.setPhoneNumber(phoneNumber);

        studentDAO.updateStudent(student);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully.");

        // Close the window
        Stage stage = (Stage) idField.getScene().getWindow();
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
