module com.gabelynch.clickmaster {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.slf4j;

    opens com.gabelynch.clickmaster to javafx.fxml;
    exports com.gabelynch.clickmaster;
}
