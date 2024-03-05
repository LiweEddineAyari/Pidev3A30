package main.projet;

import entity.Abonnement;
import entity.Account;
import interfaces.AbonnementListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.AbonnementService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AbonnementUserConroller implements Initializable, AbonnementListener {
    private static AbonnementUserConroller instance = new AbonnementUserConroller();
    public static AbonnementUserConroller getInstance() {
        return instance;
    }

    public  Abonnement selectedAbonnement;
    AbonnementService abonnementService = new AbonnementService();

    AppController appControllerinstance=AppController.getInstance();

    Account user;// user



    @FXML
    Pane PaymentPage;

    @FXML
     VBox abonnementInterfaceuser;

    @FXML
     GridPane abonnementGrid;

    @FXML
     ScrollPane abonnementScroll;
    @FXML
    Label totalPaymentPage;


    ObservableList<Abonnement> Abonnements;
    {
        try {
            Abonnements = abonnementService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance.user = appControllerinstance.account;
        {
            try {
                Abonnements = abonnementService.afficher();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        intitialisationAbonnementList();
    }




    void GoToPayment(){
        abonnementInterfaceuser.setVisible(false);
        abonnementInterfaceuser.setManaged(false);
        PaymentPage.setVisible(true);
        PaymentPage.setManaged(true);
    }


    @FXML
    void GoToAbonnement(){
        PaymentPage.setVisible(false);
        PaymentPage.setManaged(false);
        abonnementInterfaceuser.setVisible(true);
        abonnementInterfaceuser.setManaged(true);
    }

    void intitialisationAbonnementList(){
        int column = 1, row = 0;

        try {
            for (Abonnement abonnement : Abonnements) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("abonnementItemCard.fxml"));
                VBox AbonnementCard = loader.load();

                if (column == 4) {
                    row++;
                    column = 0;
                }

                AbonnementItemCard itemCardController = loader.getController();
                itemCardController.setData(abonnement,this);

                abonnementGrid.add(AbonnementCard, column++, row);
                GridPane.setMargin(AbonnementCard, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewPaymentAbonnement(Abonnement abonnement) {
        GoToPayment();
        totalPaymentPage.setText(String.valueOf(abonnement.getPrix()) + " TND");
        instance.selectedAbonnement=abonnement;

    }




    @FXML
    void handleAbonner() throws SQLException {


        System.out.println("check uno");
        System.out.println("members:"+instance.selectedAbonnement.getMembres());
        System.out.println("id:"+instance.user.getId());
      abonnementService.addnewMember(instance.selectedAbonnement,instance.user.getId());

    }






    //metier

    @FXML
    TextField minprice,maxprice,minduree,maxduree,searchAbonnementField;

    @FXML
    void handleSearchAbonnement(){

        String nameText = searchAbonnementField.getText();
        String minPriceText = minprice.getText();
        String maxPriceText = maxprice.getText();
        String minDureeText = minduree.getText();
        String maxDureeText = maxduree.getText();

        try {
            //empty abonnementGrid here
            abonnementGrid.getChildren().clear();
            Abonnements =abonnementService.searchAbonnements(minPriceText,maxPriceText,minDureeText,maxDureeText,nameText);
            intitialisationAbonnementList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
