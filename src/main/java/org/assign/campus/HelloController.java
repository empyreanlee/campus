package org.assign.campus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static org.assign.campus.directory.insertUser;

public class HelloController {
    @FXML
    private Label welcomeText, textfield, text;
    @FXML
    private TextField textField, name,password, email, confpassword;
    @FXML
    private Button submit;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        textfield.setText(textField.getText());
    }
    @FXML
    protected void onButtonClick() {
        String nameval = name.getText();
        String passwordval = password.getText();
        String emailval = email.getText();
        String cpassword = confpassword.getText();
        if (!password.getText().equals(confpassword.getText()))
            text.setText("Passwords do not match!");
        else {
            try {
                insertUser(nameval, emailval, passwordval, cpassword);
                text.setText("User successfully added!");
            } catch (SQLException e) {
                text.setText("Something went wrong!" + e.getMessage());
            }
        }

    }
}