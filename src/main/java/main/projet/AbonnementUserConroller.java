package main.projet;

import entity.Abonnement;
import entity.Coach;
import interfaces.AbonnementListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AbonnementUserConroller implements Initializable, AbonnementListener {

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



    ObservableList<Abonnement> Abonnements= FXCollections.observableArrayList(
            new Abonnement(1, 1, 101, "Gold Membership", 3, 49.99f, true),
            new Abonnement(2, 2, 102, "Silver Membership", 6, 29.99f, false),
            new Abonnement(3, 3, 103, "Bronze Membership", 12, 19.99f, true),
            new Abonnement(4, 4, 104, "Platinum Membership", 1, 99.99f, false),
            new Abonnement(5, 5, 105, "Basic Membership", 24, 9.99f, true)
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        int column = 0, row = 0;

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


    }
}
