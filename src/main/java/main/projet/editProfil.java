package main.projet;

import entity.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;



public class editProfil implements Initializable{

    @FXML
    VBox editAdminAffichage;








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editAdminAffichage.setVisible(true);
        editAdminAffichage.setManaged(true);
    }
}
