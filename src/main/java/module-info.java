module main.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;


    opens main.projet to javafx.fxml;
    exports main.projet;
    opens entity to javafx.base;
}