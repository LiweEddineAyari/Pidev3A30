package services;

import entity.gym;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface igymService <gym>{

    void ajouter(gym g) throws SQLException;
    void modifier(gym g) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<entity.gym> afficher() throws SQLException;
    ObservableList<entity.gym> search(String name) throws SQLException;


}
