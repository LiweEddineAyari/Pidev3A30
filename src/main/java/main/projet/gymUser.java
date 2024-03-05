package main.projet;

import entity.Account;

import main.projet.GymController;
import entity.gym;
import interfaces.gymListener;
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
import services.AccountService;
import services.gymService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class gymUser implements Initializable, gymListener {


    @FXML
    VBox gyminterface;
    @FXML
    Pane gympage;
    @FXML
    Label respond;
    @FXML
    Label desc;
    @FXML
    ScrollPane gymScroll;
    @FXML
    GridPane gymGrid;









    gymService gymService =new gymService();
    ObservableList<gym> gyms;

    {
        try {
            gyms = gymService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void GoToCoaches() {

        gymGrid.getChildren().clear();


        try {
            gyms = gymService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        intitialisationgymList();


        gympage.setVisible(false);
        gympage.setManaged(false);
        gyminterface.setVisible(true);
        gyminterface.setManaged(true);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intitialisationgymList();
    }

    void intitialisationgymList(){
        int column = 0, row = 0;

        try {
            for (gym g : gyms) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("gymcard.fxml"));
                VBox gymcard = loader.load();

                if (column == 4) {
                    row++;
                    column = 0;
                }

                gymitemcard itemCardController = loader.getController();
                itemCardController.setData(g,this);

                gymGrid.add(gymcard, column++, row);
                GridPane.setMargin(gymcard, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void effection(gym g) {
        gyminterface.setVisible(false);
        gyminterface.setManaged(false);
        gympage.setVisible(true);
        gympage.setManaged(true);
        int nbrplace = g.getClient_number();

        // Check if there are places left and the current user is not already signed up for this gym
        if (nbrplace == 0 && g.getUser_id() == Account.getCurrentid()) {
            respond.setText("decline attempt");
            desc.setText("No places left for this gym or you are already signed up: " + g.getNom());
        } else {
            // Check if the user is already signed up for this gym
            if (isUserAlreadySignedUp(g)) {
                respond.setText("decline attempt");
                desc.setText("You are already signed up for this gym: " + g.getNom());
            } else {
                nbrplace--;
                g.setClient_number(nbrplace);
                g.setUser_id(Account.getCurrentid());
                respond.setText("successful attempt");
                desc.setText("You have been added to this gym: " + g.getNom());

                try {
                    gymService.modifier(g);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

















    // Check if the user is already signed up for the given gym
    private boolean isUserAlreadySignedUp(gym g) {
        // Check if the gym has a user ID associated and it matches the current user's ID
        return g.getUser_id() != 0 && g.getUser_id() == Account.getCurrentid();
    }





    // respond.setText("message"+ entity.gym.getNom());
    // desc.setText("message "+ entity.gym.);


}
