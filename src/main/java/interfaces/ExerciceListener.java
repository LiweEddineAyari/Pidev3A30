package interfaces;

import entity.Exercice;


public interface ExerciceListener {
    void onViewDetailsExercice(Exercice exercice);
    void onLikeExercice(Exercice exercice);
}