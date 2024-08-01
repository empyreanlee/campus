package org.assign.campus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static org.assign.campus.course_directory.getCourseList;
import static org.assign.campus.course_directory.getNameByRegNo;

public class lecturerPageController implements Initializable {
	public Button submit;
	@FXML
	private TextField regNo, sem,ass1, ass2, cat1, cat2, exam;
	public ComboBox<String> course;
	@FXML
	private Label regNoLabel, semLabel,courseLabel,assignment, examLabel, cats ;
	final HashMap<Integer, String> marksMap = new HashMap<>();
	final ArrayList<Integer> marksList = new ArrayList<Integer>();
	Map<Integer, String> gradeMap = new TreeMap<>();

	public void initialize(String email){

	}


	public void onBtnClick(ActionEvent actionEvent) throws SQLException {
		String regNumber = regNo.getText();String semester = sem.getText();semLabel.setText(String.valueOf(sem));
		int assign1 = Integer.parseInt(ass1.getText());int assign2 = Integer.parseInt(ass2.getText());
		int Cat1 = Integer.parseInt(cat1.getText());int Cat2 = Integer.parseInt(cat2.getText());
		int Exam = Integer.parseInt(exam.getText());

		String courseName = course.getValue();

		calculateTotal(assign1, assign2, Cat1, Cat2, Exam);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ComboBox<String> course = new ComboBox<>();
		List<String> courseList;
		String regNumber = regNo.getText();
		try {
			courseList=getCourseList(regNumber);
			if (regNumber != null) {
				String name = getNameByRegNo(regNumber);
				if (name != null) regNoLabel.setText(name);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		course.getItems().addAll(courseList);

	}


	private void calculateTotal(int assign1, int assign2, int cat1, int cat2, int exam){
		int assignmentTotal = (assign1 + assign2)/2;
		int catTotal = (cat1 + cat2)/2;
		int total = assignmentTotal + catTotal + exam;
		String grade;
		/*Map.ofEntries(
				Map.entry(70, "A"),
				Map.entry(60, "B"),
				Map.entry(50, "C"),
				Map.entry(40, "D"),
				Map.entry(0, "F")
		);
		Map.Entry<Integer,String> floorEntry = gradeMap.floorEntry(total);
		return floorEntry != null ? floorEntry.getValue() : "F";
        */
		if (total >= 70) grade = "A";
		else if (total >= 60) grade = "B";
		else if (total >= 50) grade = "C";
		else if (total >= 40) grade = "D";
		else grade = "F";

		marksList.add(assignmentTotal);
		marksList.add(catTotal); marksList.add(exam);
		marksMap.put(total, grade);
		}
	}

