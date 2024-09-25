import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;  // Connection field to avoid redundant calls

    // Constructor to initialize with an existing connection
    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new student
    public void addStudent(Student student) {
        String query = "INSERT INTO Students (name, class, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getClassName());
            pstmt.setString(3, student.getPhoneNumber());
            pstmt.executeUpdate();
            System.out.println("Student added: " + student.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name"),
                        rs.getString("class"), rs.getString("phone_number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Method to fetch students by class
    public List<Student> getStudentsByClass(String className) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Students WHERE class = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, className);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(rs.getInt("id"), rs.getString("name"),
                            rs.getString("class"), rs.getString("phone_number")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Method to update student information
    public void updateStudent(Student student) {
        String query = "UPDATE Students SET name = ?, class = ?, phone_number = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getClassName());
            pstmt.setString(3, student.getPhoneNumber());
            pstmt.setInt(4, student.getId());
            pstmt.executeUpdate();
            System.out.println("Student updated: " + student.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a student by ID
    public void deleteStudent(int studentId) {
        String query = "DELETE FROM Students WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
            System.out.println("Student with ID: " + studentId + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
