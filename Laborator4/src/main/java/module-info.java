module com.example.laborator4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.willena.sqlitejdbc;
    requires apif.faker;

    opens com.example.laborator4 to javafx.fxml;
    exports com.example.laborator4;
}