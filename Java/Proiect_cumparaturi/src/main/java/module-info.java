module com.example.cumparaturimodel_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.willena.sqlitejdbc;


    opens com.example.cumparaturimodel_test to javafx.fxml;
    exports com.example.cumparaturimodel_test;
}