module main.projet {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.projet to javafx.fxml;
    exports main.projet;
}