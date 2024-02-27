package main.projet;

import entity.Account;
import interfaces.UserListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class usertemCard  {

    @FXML
     VBox coachCardItem;

    @FXML
     Label coachfirstname;

    @FXML
     Label coachname;

    Account user;
    UserListener listener;

    @FXML
    void handleViewChat(ActionEvent event) {
        if (listener != null) {
            listener.onChatView(user);
        }
    }


    public void setData(Account user,UserListener listener ) {
        this.user=user;
        this.listener=listener;

        coachname.setText(user.getNom());
        coachfirstname.setText(user.getPrenom());

    }


    @FXML
    private void handleViewChat() {
        if (listener != null) {
            listener.onChatView(user);
        }
    }
}
