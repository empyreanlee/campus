package org.assign.campus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

import static org.assign.campus.directory.*;

public class HelloController {
    public TextField regNo;
    public Label welcomeText;
    private String loggedInEmail;
    @FXML
    private Label  text;
    @FXML
    private TextField  name,password, email, confpassword;
    @FXML
    private Button submit;

    @FXML
    protected void onButtonClick(ActionEvent event) {
        String nameval = name.getText();
        String passwordval = password.getText();
        String emailval = email.getText();
        String regNumber = regNo.getText();
        String cpassword = confpassword.getText();

        if (!password.getText().equals(confpassword.getText()))
            welcomeText.setText("Passwords do not match!");
        else {
            try {
                insertUser(nameval, emailval, passwordval, cpassword);
                insertStudentDetails(regNumber);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Success: ");
                MainController mainController = new MainController();
                mainController.switchToLogin(event);
            } catch (SQLException | IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during registration: " + e.getMessage());
            }
        }
    }
    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String loginPassword = password.getText();
        String loginEmail = email.getText();

        try{
            boolean isAuthenticated = confirmUser(loginPassword, loginEmail);
            if (isAuthenticated) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome!");
                loggedInEmail = loginEmail;
                //TODO redirecting to the next page
                MainController mainController = new MainController();
                mainController.switchToHome(event, loggedInEmail);
            } else showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during authentication: " + e.getMessage());
        }
    }
    public String getLoggedInEmail(){
        return loggedInEmail;
    }
    public void onBtnClick(ActionEvent actionEvent) throws IOException {
        MainController mainController = new MainController();
        mainController.switchToRegister(actionEvent, loggedInEmail);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}