package services;

import entity.Paiment;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IPaimentService<Paiment> {
    void ajouter(Paiment paiment) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<Paiment> afficher() throws SQLException;

    void modifier(Paiment paiment)throws SQLException;


}
