import java.sql.*;

public class AttendanceDAO {
    public void markAttendance(int studentId, String date, String status) {
        String query = "INSERT INTO Attendance (student_id, date, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.setString(2, date);
            pstmt.setString(3, status);
            pstmt.executeUpdate();
            System.out.println("Attendance marked for Student ID: " + studentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
