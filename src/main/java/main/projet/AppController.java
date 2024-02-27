package main.projet;

import entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.AccountService;

import java.io.IOException;

public class AppController  {
    @FXML
    AnchorPane mainContainer;
    @FXML
    VBox adminMenu,userMenu,coachMenu;

    private static AppController instance =new AppController();
    public static AppController getInstance() {
        return instance;
    }

     Account account;

    public void sentUserData(String role, Account account) {
        instance.account=account;
        System.out.println("id : "+account.getId());
        System.out.println("role : "+account.getTitle().toString());

        if(role.equals("admin")){
            adminMenu.setVisible(true);
            adminMenu.setManaged(true);

            userMenu.setVisible(false);
            userMenu.setManaged(false);

            coachMenu.setVisible(false);
            coachMenu.setManaged(false);
        }
        if(role.equals("user")){
            adminMenu.setVisible(false);
            adminMenu.setManaged(false);

            userMenu.setVisible(true);
            userMenu.setManaged(true);

            coachMenu.setVisible(false);
            coachMenu.setManaged(false);
        }
        if(role.equals("coach")){
            adminMenu.setVisible(false);
            adminMenu.setManaged(false);

            userMenu.setVisible(false);
            userMenu.setManaged(false);

            coachMenu.setVisible(true);
            coachMenu.setManaged(true);
        }
    }



    @FXML
    public void EditInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editProfil.fxml"));
        Parent editadminRoot = loader.load();

        mainContainer.getChildren().setAll(editadminRoot);
    }


    @FXML
    public void adminInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent accountRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(accountRoot);
    }

    @FXML
    public void userInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
        Parent userRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(userRoot);
    }

    // open product interface
    @FXML
    public void productInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("product.fxml"));
        Parent productRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(productRoot);
    }


    //open coach interface
    @FXML
    public void coachInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("coach.fxml"));
        Parent coachRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(coachRoot);
    }

    //open abonnement interface
    @FXML
    public void abonnementInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("abonnement.fxml"));
        Parent abonnementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(abonnementRoot);
    }
    @FXML
    public void evennementInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("evennement.fxml"));
        Parent evennementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(evennementRoot);
    }

    @FXML
    public void PaimentInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paiment.fxml"));
        Parent evennementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(evennementRoot);
    }

    @FXML
    public void LogOut(ActionEvent event) throws IOException {
        instance.account=null;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("loginPage.fxml"));
        Parent root=loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1202, 535);
        scene.getStylesheets().add("file:/C:/Users/21690/IdeaProjects/projet/src/main/resources/main/projet/css/LoginPage.css");
        stage.setScene(scene);
        stage.setTitle("FitHub Pro");
        stage.setResizable(false);
        stage.show();
    }


   // client Menu

    @FXML
    public void ProductUserInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("productUser.fxml"));
        Parent evennementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(evennementRoot);
    }

    @FXML
    public void CoachUserInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("coachUser.fxml"));
        Parent evennementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(evennementRoot);
    }
    @FXML
    public void AbonnementUserInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("abonnementUser.fxml"));
        Parent evennementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(evennementRoot);
    }

    @FXML
    public void ShopUserInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopUser.fxml"));
        Parent evennementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(evennementRoot);
    }

    @FXML
    public void EventUserInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("evennementUser.fxml"));
        Parent evennementRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(evennementRoot);
    }


    @FXML
    public void UserCoachInterfaceLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userInCoachInterface.fxml"));
        Parent userRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(userRoot);
    }

    @FXML
    public void PlanningPageLoad()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("planning.fxml"));
        Parent userRoot = loader.load();
        // Set the loaded FXML as a child of mainContainer
        mainContainer.getChildren().setAll(userRoot);
    }


}
