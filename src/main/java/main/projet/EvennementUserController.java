package main.projet;

import entity.Evennement;
import interfaces.EvennementListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EvennementUserController implements Initializable, EvennementListener {

    @FXML
     Pane AvisPage;
    @FXML
     VBox EvennementInterfaceuser;
    @FXML
     GridPane eventGrid;

    @FXML
     ScrollPane eventScroll;
    ObservableList<Evennement> evennements= FXCollections.observableArrayList(
            new Evennement(1, "Event 1", "2024-02-01", "Type 1", "Description 1"),
            new Evennement(2, "Event 2", "2024-02-15", "Type 2", "Description 2"),
            new Evennement(3, "Event 3", "2024-03-05", "Type 1", "Description 3"),
            new Evennement(4, "Event 4", "2024-03-20", "Type 3", "Description 4"),
            new Evennement(5, "Event 5", "2024-04-10", "Type 2", "Description 5")
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intitialisationEvenList();
    }

    void intitialisationEvenList(){
        int column = 0, row = 0;

        try {
            for (Evennement evennement : evennements) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("evennementItemCard.fxml"));
                VBox evennementCard = loader.load();

                if (column == 4) {
                    row++;
                    column = 0;
                }

                EvennementItemCard itemCardController = loader.getController();
                itemCardController.setData(evennement,this);

                eventGrid.add(evennementCard, column++, row);
                GridPane.setMargin(evennementCard, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    void GoToAvisPage(){
        EvennementInterfaceuser.setVisible(false);
        EvennementInterfaceuser.setManaged(false);

        AvisPage.setVisible(true);
        AvisPage.setManaged(true);

    }

    @FXML
    void GoToEvennementPage(){
        AvisPage.setVisible(false);
        AvisPage.setManaged(false);

        EvennementInterfaceuser.setVisible(true);
        EvennementInterfaceuser.setManaged(true);

    }

    @Override
    public void onViewAvis(Evennement evennement) {
        GoToAvisPage();

    }

    @Override
    public void onParticiper(Evennement evennement) {

    }
}
