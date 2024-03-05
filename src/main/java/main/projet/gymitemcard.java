package main.projet;

import entity.Account;
import entity.gym;
import interfaces.CoachListener;
import interfaces.gymListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class gymitemcard {
    @FXML
    Label coachfirstname;

    @FXML
    Label coachname;
    gym g;
    private gymListener listener;
    public void setData(gym g,gymListener listener) {
        this.listener=listener;
        this.g = g;
        coachname.setText(g.getNom());
        coachfirstname.setText(String.valueOf(g.getClient_number()));
    }

    @FXML
    private void handleViewChat() {
        if (listener != null) {
            listener.effection(g);
        }
    }
}
