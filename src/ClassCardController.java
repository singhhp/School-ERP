import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ClassCardController {
    @FXML
    private Text classNameText;

    private AdminDashboardController adminDashboardController;

    public void setClassName(String className) {
        classNameText.setText(className);
    }

    public void setAdminDashboardController(AdminDashboardController controller) {
        this.adminDashboardController = controller;
    }

    @FXML
    private void handleViewStudents() {
        String className = classNameText.getText();
        adminDashboardController.showStudentsForClass(className); // Call to show students
    }
}
