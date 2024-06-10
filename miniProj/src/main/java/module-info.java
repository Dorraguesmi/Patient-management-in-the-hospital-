module com.example.miniproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens com.example.miniproj to javafx.fxml;
    opens model to javafx.base;
    exports com.example.miniproj;
}
