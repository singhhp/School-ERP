import java.util.List;
import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();
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
            scanner.nextLine(); // consume newline

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
                    scanner.nextLine(); // consume newline
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

        scanner.close();
    }
}
