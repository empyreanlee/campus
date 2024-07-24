module org.assign.campus {
    requires javafx.controls;
    requires javafx.fxml;

	requires java.sql;
    requires jbcrypt;

	opens org.assign.campus to javafx.fxml;
    exports org.assign.campus;
}