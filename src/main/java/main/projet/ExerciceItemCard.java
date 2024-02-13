package main.projet;

import entity.Exercice;
import entity.Product;
import interfaces.ExerciceListener;
import interfaces.ProductListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ExerciceItemCard {

    @FXML
     Label exercicename;

    @FXML
     Label type;

    @FXML
     VBox productCardItem;
    Exercice exercice;
    private ExerciceListener exerciceListener;
    public void setDataa(Exercice exercice,ExerciceListener exerciceListener) {

        this.exercice = exercice;
        this.exerciceListener=exerciceListener;
        exercicename.setText(exercice.getName());
        type.setText(exercice.getType());
    }

    @FXML
    private void handleViewDetailsButton() {
        if (exerciceListener != null) {
            exerciceListener.onViewDetailsExercice(exercice);
        }
    }
    @FXML
    private void handleLikeButton() {
        if (exerciceListener != null) {
            exerciceListener.onLikeExercice(exercice);
        }
    }

}
