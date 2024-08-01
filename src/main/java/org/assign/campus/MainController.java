package org.assign.campus;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToRegister(ActionEvent event, String loggedInEmail) throws IOException {
        FXMLLoader Loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("RegisterCourses.fxml")));
        root = Loader.load();
        studentCourseController controller = Loader.getController();
        controller.initialize(loggedInEmail);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToStudentPage(ActionEvent event, String email) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("student_page.fxml")));
        root = loader.load();
        studentPageController controller = loader.getController();
        controller.initialize(email);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sign.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLecturer(ActionEvent event, String email) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("lecturerPage.fxml")));
        root = loader.load();

        lecturerPageController controller = loader.getController();
        controller.initialize(email);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
