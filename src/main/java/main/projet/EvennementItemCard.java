package main.projet;

import entity.Evennement;
import interfaces.EvennementListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EvennementItemCard {

    @FXML
    private Label eventname;

    @FXML
    private Label eventtype;

    Evennement evennement;
    private  EvennementListener listener;
    public void setData(Evennement evennement,EvennementListener listener) {
        this.evennement = evennement;
        this.listener=listener;
        eventname.setText(evennement.getNom());
        eventtype.setText(evennement.getType());
    }

    @FXML
    void handleParticiper(){
        if (listener != null) {
            listener.onParticiper(evennement);
        }
    }
    @FXML
    void handleViewAvis(){
        if (listener != null) {
            listener.onViewAvis(evennement);
        }
    }

}
