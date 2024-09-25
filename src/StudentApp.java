import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            // Create the database connection
            String url = "jdbc:mysql://localhost:3306/attendance_db"; // Replace with your database URL
            String user = "root"; // Replace with your database username
            String password = "Hps@7892804255"; // Replace with your database password
            connection = DriverManager.getConnection(url, user, password);

            StudentDAO studentDAO = new StudentDAO(connection);  // Pass the connection to the DAO

            boolean running = true;

            while (running) {
                System.out.println("\n--- Attendance Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. View All Students");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Add a new student
                        System.out.println("\n--- Add New Student ---");
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Class: ");
                        String className = scanner.nextLine();
                        System.out.print("Phone Number: ");
                        String phoneNumber = scanner.nextLine();

                        Student newStudent = new Student(0, name, className, phoneNumber);
                        studentDAO.addStudent(newStudent);
                        break;

                    case 2: // Update a student record
                        System.out.println("\n--- Update Student ---");
                        System.out.print("Enter Student ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("New Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("New Class: ");
                        String newClass = scanner.nextLine();
                        System.out.print("New Phone Number: ");
                        String newPhone = scanner.nextLine();

                        Student updatedStudent = new Student(updateId, newName, newClass, newPhone);
                        studentDAO.updateStudent(updatedStudent);
                        break;

                    case 3: // Delete a student record
                        System.out.println("\n--- Delete Student ---");
                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        studentDAO.deleteStudent(deleteId);
                        break;

                    case 4: // View all students
                        System.out.println("\n--- All Students ---");
                        List<Student> students = studentDAO.getAllStudents();
                        for (Student student : students) {
                            System.out.println("ID: " + student.getId() + ", Name: " + student.getName() +
                                    ", Class: " + student.getClassName() + ", Phone: " + student.getPhoneNumber());
                        }
                        break;

                    case 5: // Exit
                        running = false;
                        System.out.println("Exiting the system. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
