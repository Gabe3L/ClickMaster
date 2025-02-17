module com.gabelynch.clickmaster {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.gabelynch.clickmaster to javafx.fxml;
    exports com.gabelynch.clickmaster;
}
