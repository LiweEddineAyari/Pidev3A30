package services;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IChatConversationService<ChatConversation> {

    void ajouter(ChatConversation chatConversation) throws SQLException;
    void modifier(ChatConversation chatConversation) throws SQLException;
    ObservableList<ChatConversation> getChatConversationsList(int id_chatSession) throws SQLException;
    void deleteConversationsByChatSessionId(int id_chatSession) throws SQLException;

    int getAdminIdByPlanningId(int planningId, int idcoach);
}
