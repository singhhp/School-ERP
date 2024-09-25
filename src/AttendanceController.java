import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {
    @FXML
    private TableView<Attendance> attendanceTable;
    @FXML
    private TableColumn<Attendance, String> studentIdColumn;
    @FXML
    private TableColumn<Attendance, String> studentNameColumn;
    @FXML
    private TableColumn<Attendance, String> dateColumn;
    @FXML
    private TableColumn<Attendance, String> statusColumn;

    private Connection connection;

    public AttendanceController() {
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
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void loadAttendanceForClass(String className) {
        List<Attendance> attendanceList = new ArrayList<>();
        try {
            String query = "SELECT a.id, s.name, a.date, a.status " +
                    "FROM attendance a JOIN students s ON a.id = s.id " +
                    "WHERE s.class = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, className);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int Id = resultSet.getInt("id");
                int studentId = resultSet.getInt("studentid");
               // String studentName = resultSet.getString("name");
                String date = resultSet.getString("date");
                String status = resultSet.getString("status");

                attendanceList.add(new Attendance(Id,studentId, date, status));
            }

            attendanceTable.getItems().setAll(attendanceList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
