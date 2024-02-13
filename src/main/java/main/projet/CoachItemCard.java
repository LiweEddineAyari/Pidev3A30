package main.projet;

import entity.Coach;
import entity.Product;
import interfaces.CoachListener;
import interfaces.ProductListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CoachItemCard {
    @FXML
     Label coachfirstname;

    @FXML
     Label coachname;
    Coach coach;
     private CoachListener listener;
    public void setData(Coach coach,CoachListener listener) {
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
