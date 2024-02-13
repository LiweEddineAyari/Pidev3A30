package main.projet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
     VBox Vboxlogin;
    @FXML
     AnchorPane loginpane;
    @FXML
     AnchorPane signuppane;
    @FXML
    ImageView imgview;
    @FXML
    ToggleGroup role1;


    public String getSelectedRole() {
        if (role1.getSelectedToggle() != null) {
            RadioButton selectedRadio = (RadioButton) role1.getSelectedToggle();
            return selectedRadio.getText();
        }
        return "Admin"; // If no radio button is selected
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image(getClass().getResourceAsStream("images/FitHubPro.png"));
        imgview.setImage(image);
        loginpane.setVisible(true);
        signuppane.setVisible(false);
    }

    public void switchToSignUp(){
        loginpane.setVisible(false);
        loginpane.setManaged(false);
        signuppane.setVisible(true);
    }

    public void switchToLogin(){
        signuppane.setVisible(false);
        signuppane.setManaged(false);
        loginpane.setVisible(true);
    }


    public void login(ActionEvent event) throws IOException {



        FXMLLoader loader=new FXMLLoader(getClass().getResource("app.fxml"));
        Parent root=loader.load();


        AppController appController = loader.getController();
        appController.sentUserData(getSelectedRole());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1600, 900);
        scene.getStylesheets().add("file:/C:/Users/JAXIM/IdeaProjects/projet/src/main/resources/main/projet/css/app.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}