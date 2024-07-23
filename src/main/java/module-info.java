module org.assign.campus {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires jbcrypt;
    requires jdk.compiler;
    requires java.desktop;
    requires jdk.xml.dom;

    opens org.assign.campus to javafx.fxml;
    exports org.assign.campus;
}