package org.assign.campus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class studentPageController implements Initializable {
	@FXML
	private String email;


	public void initialize(String email) {
		this.email = email;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}
}
