import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;
import java.io.IOException;

public class AdminDashboardController {
    @FXML
    private FlowPane classCardPane;

    private Connection connection;
    private StudentDAO studentDAO;  // StudentDAO instance

    public AdminDashboardController() {
        try {
            // Database connection setup
            String url = "jdbc:mysql://localhost:3306/attendance_db"; // Replace with your database URL
            String user = "root"; // Replace with your database username
            String password = "Hps@7892804255"; // Replace with your database password
            connection = DriverManager.getConnection(url, user, password);

            // Initialize StudentDAO
            studentDAO = new StudentDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        loadClassCards();
    }

    private void loadClassCards() {
        try {
            String query = "SELECT DISTINCT class FROM students"; // Query to fetch distinct classes
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String className = resultSet.getString("class");
                addClassCard(className);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addClassCard(String className) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassCard.fxml"));
            AnchorPane card = loader.load();
            ClassCardController cardController = loader.getController();
            cardController.setClassName(className);
            cardController.setAdminDashboardController(this);
            classCardPane.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudent.fxml"));
            GridPane addStudentView = loader.load();

            // Get the controller of AddStudent.fxml
            AddStudentController addStudentController = loader.getController();
            addStudentController.setStudentDAO(studentDAO);  // Inject StudentDAO into the AddStudentController

            Stage stage = new Stage();
            stage.setTitle("Add Student");
            stage.setScene(new Scene(addStudentView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRefresh() {
        classCardPane.getChildren().clear();
        loadClassCards();
    }

    // Method to show attendance for a specific class
    public void showAttendanceForClass(String className) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Attendance.fxml"));
            AnchorPane attendanceView = loader.load();
            AttendanceController attendanceController = loader.getController();
            attendanceController.loadAttendanceForClass(className); // Load attendance data for the class

            Stage stage = new Stage();
            stage.setTitle("Attendance Records for " + className);
            stage.setScene(new Scene(attendanceView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to show students for a specific class
    public void showStudentsForClass(String className) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentViewController.fxml"));  // Corrected FXML file name
            AnchorPane studentsView = loader.load();
            StudentsViewController studentsViewController = loader.getController();
            studentsViewController.loadStudentsForClass(className); // Load students for the class

            Stage stage = new Stage();
            stage.setTitle("Students in " + className);
            stage.setScene(new Scene(studentsView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
