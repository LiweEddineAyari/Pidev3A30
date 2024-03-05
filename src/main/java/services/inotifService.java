package services;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface inotifService<notif> {

    void ajouter(notif notif) throws SQLException;
    ObservableList<notif> afficher() throws SQLException;
}
