package org.assign.campus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class StudentController {
    public Button register;
    public GridPane gridPane;
    public ComboBox<String> semester1;
    public ComboBox<String> semester2;

    @FXML
    protected void onRegisterButtonClick() {

    }

    public void initialize() {
        String[] courses1 ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] courses2 ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        semester1.getItems().addAll(courses1);
        semester2.getItems().addAll(courses2);

    }
}
