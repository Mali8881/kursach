module org.example.kourswork {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires jdk.httpserver;
    opens org.example.kourswork to javafx.fxml;
    exports org.example.kourswork;
}
