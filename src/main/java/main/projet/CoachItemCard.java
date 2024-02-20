package main.projet;

import entity.Account;
import interfaces.CoachListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CoachItemCard {
    @FXML
     Label coachfirstname;

    @FXML
     Label coachname;
    Account coach;
     private CoachListener listener;
    public void setData(Account coach,CoachListener listener) {
        this.listener=listener;
        this.coach = coach;
        coachname.setText(coach.getNom());
        coachfirstname.setText(coach.getPrenom());
    }

    @FXML
    private void handleViewChat() {
        if (listener != null) {
            listener.onChatView(coach);
        }
    }
}
