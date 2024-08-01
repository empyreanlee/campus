package org.assign.campus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.assign.campus.course_directory.*;

public class studentCourseController implements Initializable {
    public String email;
    public Button register;
    public GridPane gridPane;
    @FXML
    private Label top;


    @FXML
    private ListView<String> listView1;
    @FXML
    private ListView<String> listView2;

    private final ArrayList<String> selectedCourses = new ArrayList<>();
    private final ArrayList<String> selectedCourses2 = new ArrayList<>();

    public void initialize(String email) {
        this.email = email;
       // System.out.println("Email set to: " + email); // Debugging lineif (email != null) {top.setText(email);
    }

    @FXML
    protected void onRegisterBtnClick(ActionEvent event) throws IOException {
        List<String> selectedItems = listView1.getSelectionModel().getSelectedItems();
        List<String> selectedItems2 = listView2.getSelectionModel().getSelectedItems();
        selectedCourses.clear();
        selectedCourses.addAll(selectedItems);
        selectedCourses2.addAll(selectedItems2);

        try {
            if (selectedCourses.isEmpty() || selectedCourses2.isEmpty()){showAlert(Alert.AlertType.CONFIRMATION, "INFO", "Please select one of the following courses!");
                return;}
            if (selectedCourses.size()>5 && selectedCourses2.size()>5) { showAlert(Alert.AlertType.ERROR, "Error", "Select only 5 courses!");
                return;}
            String regNumber = getRegNobyEmail(email);
            System.out.println("Reg set to: " + regNumber);
            if (regNumber != null) {
                int studentId = getStudentIdByRegNo(regNumber);
                if (studentId != -1) {
                    registerCourses(studentId, selectedCourses, selectedCourses2);
                    showAlert(Alert.AlertType.CONFIRMATION, "Registration Successful", "Courses registered successfully!");
                    MainController mainController = new MainController();
                    mainController.switchToStudentPage(event, email);
                } else showAlert(Alert.AlertType.ERROR, "Error", "Student not found!");
            } else showAlert(Alert.AlertType.ERROR, "Error", "Registration number not found for the user.");
        }
        catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during course registration: " + e.getMessage());
        }
        MainController mainController = new MainController();
        mainController.switchToStudentPage(event, email);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        String[] courses1 ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String[] courses2 ={"Mond", "Tuesday", "Wednesday", "Thursday", "Friday"};
        listView1.getItems().addAll(courses1);listView2.getItems().addAll(courses2);
        listView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
