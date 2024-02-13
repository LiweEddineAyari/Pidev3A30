package services;

import entity.Exercice;
import entity.Product;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IExerciceService<Exercice> {

    void ajouter(Exercice exercice) throws SQLException;
    void modifier(Exercice product) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<entity.Exercice> afficher() throws SQLException;
    ObservableList<entity.Exercice> search(String name) throws SQLException;
    public ObservableList<entity.Exercice> searchU(String name, String target, String type) throws SQLException;
    public ObservableList<entity.Exercice> getExercisesByProductId(int productId) throws SQLException ;
    public ObservableList<entity.Exercice> getExercisesByCriteria(String bodyPart, String type, String intensity, String equipmentNeeded, int count) throws SQLException;

}
