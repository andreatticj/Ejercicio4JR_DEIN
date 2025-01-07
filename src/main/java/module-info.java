module eu.andreatt.ejercicio4jr_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;

    exports eu.andreatt.ejercicio4jr_dein.controller;
    opens eu.andreatt.ejercicio4jr_dein.controller to javafx.fxml;
    exports eu.andreatt.ejercicio4jr_dein.application;
    opens eu.andreatt.ejercicio4jr_dein.application to javafx.fxml;
}