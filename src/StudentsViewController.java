import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsViewController {
    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, String> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> classColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;

    private Connection connection;
    private StudentDAO studentDAO;

    public StudentsViewController() {
        try {
            String url = "jdbc:mysql://localhost:3306/attendance_db"; // Replace with your database URL
            String user = "root"; // Replace with your database username
            String password = "Hps@7892804255"; // Replace with your database password
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    public void loadStudentsForClass(String className) {
        List<Student> studentList = new ArrayList<>();
        try {
            String query = "SELECT * FROM students WHERE class = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, className);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String studentClass = resultSet.getString("class");
                String phone_number = resultSet.getString("phone_number");

                studentList.add(new Student(id, name, studentClass, phone_number));
            }

            studentsTable.getItems().setAll(studentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleUpdateStudent() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null)  {
            showUpdateStudentDialog(selectedStudent);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student to update.");
            alert.showAndWait();
        }
    }
    private void showUpdateStudentDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateStudent.fxml"));
            GridPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Student");
            dialogStage.setScene(new Scene(page));

            UpdateStudentController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStudent(student);
            controller.setStudentDAO(studentDAO);

            dialogStage.showAndWait();
            if (controller.isOkClicked()) {
                loadStudentsForClass(student.getClassName()); // Reload the students to refresh the table
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteStudent() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            // Implement delete functionality
            try {
                String deleteQuery = "DELETE FROM students WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, selectedStudent.getId());
                preparedStatement.executeUpdate();

                // Refresh the table
                loadStudentsForClass(selectedStudent.getClassName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student to delete.");
            alert.showAndWait();
        }
    }
}
