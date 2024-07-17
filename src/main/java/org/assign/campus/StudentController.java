package org.assign.campus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public Button register;
    public GridPane gridPane;

    @FXML
    private ListView<String> listView1;
    @FXML
    private ListView<String> listView2;

    private final ArrayList<String> selectedCourses = new ArrayList<>();


    @FXML
    protected void onRegisterButtonClick() {
        List<String> selectedItems = listView1.getSelectionModel().getSelectedItems();
        selectedCourses.clear();
        selectedCourses.addAll(selectedItems);
        for (String course : selectedCourses){
            System.out.println(course);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] courses1 ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] courses2 ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        listView1.getItems().addAll(courses1);
        //listView2.getItems().addAll(courses2);
        listView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
}
