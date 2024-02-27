package services;

import entity.Exercice;
import entity.Product;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Map;

public interface IExerciceService<Exercice> {

    void ajouter(Exercice exercice) throws SQLException;
    void modifier(Exercice product) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<entity.Exercice> afficher() throws SQLException;
    ObservableList<entity.Exercice> search(String name) throws SQLException;
    public ObservableList<entity.Exercice> searchU(String name, String target, String type) throws SQLException;
    public ObservableList<entity.Exercice> getExercisesByProductId(int productId) throws SQLException ;
    public ObservableList<entity.Exercice> getExercisesByCriteria(String bodyPart, String type, String intensity, String equipmentNeeded, int count) throws SQLException;

    void ajouterFavoriteExercice(int idUser, int idExercice, String type) throws SQLException;

    int getAidFromFavoriteEx(int idExercice,int iduser) throws SQLException;

    void supprimerFavoriteExercice(int idUser, int idExercice) throws SQLException;

    void updateScore(String type, int delta) throws SQLException;

    Map<String, Integer> getExercicesWithGreatestScoret() throws SQLException;
}
