package services;

import java.sql.SQLException;

public interface IChatSessionService <chatSession>{

    void ajouter(chatSession chatSession) throws SQLException;
    void supprimer(int id) throws SQLException;
    int getChatSession(chatSession chatSession) throws SQLException;
}
