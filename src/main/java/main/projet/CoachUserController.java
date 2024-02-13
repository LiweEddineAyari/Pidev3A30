package main.projet;

import entity.Coach;
import entity.Product;
import interfaces.CoachListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoachUserController implements Initializable, CoachListener {


    @FXML
     VBox coachInterfaceuser;
    @FXML
    Pane chatPage;
     @FXML
    Label ChatTitle;
    @FXML
     ScrollPane coachScroll;
    @FXML
     GridPane coachGrid;

    ObservableList<Coach> coaches= FXCollections.observableArrayList(
            new Coach(1, "Smith", "John", 35, "john.smith@email.com", 123456),
            new Coach(2, "Doe", "Jane", 28, "jane.doe@email.com", 654321),
            new Coach(3, "Williams", "Tom", 42, "tom.williams@email.com", 987654),
            new Coach(4, "Johnson", "Alice", 30, "alice.johnson@email.com", 111222),
            new Coach(5, "Brown", "Chris", 50, "chris.brown@email.com", 333444)
    );

    @FXML
    void GoToCoaches(){
        chatPage.setVisible(false);
        chatPage.setManaged(false);
        coachInterfaceuser.setVisible(true);
        coachInterfaceuser.setManaged(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intitialisationCoachesList();
    }

    void intitialisationCoachesList(){
        int column = 0, row = 0;

        try {
            for (Coach coach : coaches) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("coachItemCard.fxml"));
                VBox coachCard = loader.load();

                if (column == 4) {
                    row++;
                    column = 0;
                }

                CoachItemCard itemCardController = loader.getController();
                itemCardController.setData(coach,this);

                coachGrid.add(coachCard, column++, row);
                GridPane.setMargin(coachCard, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChatView(Coach coach) {
        coachInterfaceuser.setVisible(false);
        coachInterfaceuser.setManaged(false);
        chatPage.setVisible(true);
        chatPage.setManaged(true);
        ChatTitle.setText("Chat With "+coach.getNom()+" "+coach.getPrenom());
    }
}
