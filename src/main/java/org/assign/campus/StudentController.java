package org.assign.campus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.assign.campus.courseDir.registerCourses;

public class StudentController implements Initializable {
    public Button register;
    public GridPane gridPane;

    @FXML
    private ListView<String> listView1;
    @FXML
    private ListView<String> listView2;

    private final ArrayList<String> selectedCourses = new ArrayList<>();
    private final ArrayList<String> selectedCourses2 = new ArrayList<>();



    @FXML
    protected void onRegisterButtonClick() {
        List<String> selectedItems = listView1.getSelectionModel().getSelectedItems();
        List<String> selectedItems2 = listView2.getSelectionModel().getSelectedItems();
        selectedCourses.clear();
        selectedCourses.addAll(selectedItems);
        selectedCourses2.addAll(selectedItems2);
        for (String course : selectedCourses){
            System.out.println(course);
        }
        try{
            registerCourses(selectedCourses);
            showAlert(Alert.AlertType.CONFIRMATION, "Registration Successful", "Courses registered successfully!");
        } catch (SQLException e){
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during course registration: " + e.getMessage());
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] courses1 ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] courses2 ={"Mond", "Tuesday", "Wednesday", "Thursday", "Friday"};

        listView1.getItems().addAll(courses1);
        listView2.getItems().addAll(courses2);
        listView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
