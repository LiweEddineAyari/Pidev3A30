package main.projet;

import entity.Account;
import entity.Exercice;
import entity.Product;
import interfaces.ExerciceListener;
import interfaces.ProductListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ExerciseService;

import java.sql.SQLException;

public class ExerciceItemCard {

    @FXML
     Label exercicename;

    @FXML
     Label type;

    @FXML
    Button likebtn;
    @FXML
    ImageView likebtnimg;


    @FXML
     VBox productCardItem;
    Exercice exercice;
    private ExerciceListener exerciceListener;
    public void setDataa(Exercice exercice,ExerciceListener exerciceListener,Boolean isLiked) {

        this.exercice = exercice;
        this.exerciceListener=exerciceListener;
        exercicename.setText(exercice.getName());
        type.setText(exercice.getType());

        if (isLiked==true){
            Image likedImage = new Image("file:/C:/Users/JAXIM/IdeaProjects/projet/src/main/resources/main/projet/icons/liked.png");
            ImageView iconImageView = new ImageView(likedImage);
            ((ImageView) likebtn.getGraphic()).setImage(iconImageView.getImage());
        }

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
            Image likedImage = new Image("file:/C:/Users/JAXIM/IdeaProjects/projet/src/main/resources/main/projet/icons/liked.png");
            Image defaultImage = new Image("file:/C:/Users/JAXIM/IdeaProjects/projet/src/main/resources/main/projet/icons/like.png");
            Account user = AppController.getInstance().account;
            ExerciseService exerciseService = new ExerciseService();
            try {
                System.out.println("ex id : "+exercice.getId());
                System.out.println("ex idd : "+exerciseService.getAidFromFavoriteEx(exercice.getId(), user.getId()));

                if ( exercice.getId() == exerciseService.getAidFromFavoriteEx(exercice.getId(), user.getId()) ) {
                    // If the current image is the liked image, set it back to the default image
                    System.out.println("like");
                    exerciseService.supprimerFavoriteExercice( user.getId() , exercice.getId());
                    exerciseService.updateScore(exercice.getType(), -1);
                    ImageView iconImageView = new ImageView(defaultImage);
                    ((ImageView) likebtn.getGraphic()).setImage(null);
                    ((ImageView) likebtn.getGraphic()).setImage(iconImageView.getImage());

                } else {
                    exerciceListener.onLikeExercice(exercice);
                    exerciseService.updateScore(exercice.getType(), +1);
                    System.out.println("liked");
                    ImageView iconImageView = new ImageView(likedImage);
                    ((ImageView) likebtn.getGraphic()).setImage(null);
                    ((ImageView) likebtn.getGraphic()).setImage(iconImageView.getImage());
                }



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
