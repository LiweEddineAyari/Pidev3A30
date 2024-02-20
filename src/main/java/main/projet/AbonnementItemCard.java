package main.projet;

import entity.Abonnement;
import interfaces.AbonnementListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AbonnementItemCard {
    @FXML
     Label abonnementCategory;

    @FXML
     Label abonnementname;
    Abonnement abonnement;
    private AbonnementListener listener;
    public void setData(Abonnement abonnement,AbonnementListener listener) {
        this.abonnement = abonnement;
        this.listener=listener;
        abonnementname.setText(abonnement.getNom());
        abonnementCategory.setText(String.valueOf(abonnement.getIdcategory()));
    }

    @FXML
    private void handleViewChat() {
        if (listener != null) {
            listener.onViewPaymentAbonnement(abonnement);
        }
    }
}
