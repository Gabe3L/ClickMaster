module com.gabelynch.clickmaster {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.gabelynch.clickmaster to javafx.fxml;
    exports com.gabelynch.clickmaster;
}
