module tesi.tesifx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;


    opens tesi.tesifx to javafx.fxml;
    exports tesi.tesifx;
}