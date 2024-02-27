package services;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IPlanningService<Planning> {

    int ajouter(Planning planning) throws SQLException;
    void modifier(Planning planning) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<Planning> afficher() throws SQLException;
}
