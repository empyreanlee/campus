package org.assign.campus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.assign.campus.directory.*;

public class HelloController implements Initializable {


    public TextField regNo;
    public Label welcomeText;
    public ComboBox<String> role;
    private String loggedInEmail;
    @FXML
    private TextField  name,password, email, confpassword;
    @FXML
    private Button submit, signIn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //role.getItems().addAll("student", "teacher");
        //
        }
    @FXML
    protected void onBtnClick(ActionEvent event) throws IOException {
        MainController controller = new MainController();
        controller.switchToLogin(event);
    }
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
                insertStudentDetails(regNumber, emailval);
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
				boolean hasRegisteredCourses = isRegisteredCourses(loggedInEmail);
                MainController mainController = new MainController();
				if (hasRegisteredCourses) mainController.switchToStudentPage(event,loggedInEmail);
				else mainController.switchToRegister(event,loggedInEmail);
            } else showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during authentication: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}