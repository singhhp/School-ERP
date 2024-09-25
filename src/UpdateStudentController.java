import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateStudentController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField classField;

    private Stage dialogStage;
    private Student student;
    private StudentDAO studentDAO;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStudent(Student student) {
        this.student = student;
        nameField.setText(student.getName());
        phoneField.setText(student.getPhoneNumber());
    }

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk(ActionEvent event) {
//        if (isInputValid()) {
//            student.setName(nameField.getText());
//            student.setPhoneNumber(phoneField.getText());
//
//            studentDAO.updateStudent(student); // Update the student in the database
//            okClicked = true;
//            dialogStage.close();
//        }
        int id = student.getId();
        String name = nameField.getText().trim();
        String className = classField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        if (name.isEmpty() || className.isEmpty() || phoneNumber.isEmpty()) {
          //  showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        // Optionally, add validation for phone number format

        Student newStudent = new Student(id, name, className, phoneNumber);
        studentDAO.updateStudent(newStudent);
       // showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully.");

        // Close the window
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        // Implement validation logic
        return true;
    }
}