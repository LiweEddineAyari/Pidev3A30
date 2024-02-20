package main.projet;

import entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.AccountService;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    AccountService accountService =new AccountService();


    @FXML
     TextField mailSignup,nameSignup,passwordSignup,ageSignup,firstnameSignup,passwordlogin,maillogin;
    void initAdminInputs(){
        nameSignup.setText("");
        firstnameSignup.setText("");
        ageSignup.setText("");
        mailSignup.setText("");
        passwordSignup.setText("");
    }

     @FXML
     void handleSignUp(){
         String name = nameSignup.getText();
         String prenom =  firstnameSignup.getText();
         int age =  Integer.parseInt(ageSignup.getText());
         String mail = mailSignup.getText();
         String password =  passwordSignup.getText();

         Account account = new Account(-1, name, prenom, age, mail, password,Account.Title.user);
         AccountService accountService =new AccountService();

         try {
             accountService.ajouter(account);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         switchToLogin();
         initAdminInputs();
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


    public void login(ActionEvent event) throws IOException, SQLException {

        String email="";
        String password="";

         email = maillogin.getText();
         password =  passwordlogin.getText();

        Account account = accountService.authenticate(email,password);
        System.out.println(account);
        if(account!=null){
            FXMLLoader loader=new FXMLLoader(getClass().getResource("app.fxml"));
            Parent root=loader.load();


            AppController appController = loader.getController();
            String role="";
            if(account.getTitle().equals(Account.Title.admin)){role="admin";}
            if(account.getTitle().equals(Account.Title.user)){role="user";}
            if(account.getTitle().equals(Account.Title.coach)){role="coach";}

            appController.sentUserData(role,account);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1600, 900);
            scene.getStylesheets().add("file:/C:/Users/JAXIM/IdeaProjects/projet/src/main/resources/main/projet/css/app.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        else {

        }



    }

}