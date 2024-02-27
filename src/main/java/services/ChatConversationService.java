package services;

import entity.ChatConversation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.*;

public class ChatConversationService implements IChatConversationService<ChatConversation> {
    private Connection connection;

    public ChatConversationService() {
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(ChatConversation chatConversation) throws SQLException {
        String sql = "INSERT INTO chat_conversation (id_chatSession, id_sender, id_reciever,idplanning, message) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, chatConversation.getId_chatSession());
            statement.setInt(2, chatConversation.getId_sender());
            statement.setInt(3, chatConversation.getId_reciever());
            statement.setInt(4, chatConversation.getIdplanning());
            statement.setString(5, chatConversation.getMessage());

            statement.executeUpdate();
        }
    }

    @Override
    public void modifier(ChatConversation chatConversation) throws SQLException {
        String sql = "UPDATE chat_conversation SET message = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chatConversation.getMessage());
            statement.setInt(2, chatConversation.getId());

            statement.executeUpdate();
        }
    }
    @Override
    public ObservableList<ChatConversation> getChatConversationsList(int id_chatSession) throws SQLException {
        ObservableList<ChatConversation> conversations = FXCollections.observableArrayList();
        String sql = "SELECT * FROM chat_conversation WHERE id_chatSession = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_chatSession);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int id_sender = resultSet.getInt("id_sender");
                    int id_reciever = resultSet.getInt("id_reciever");
                    int idplanning = resultSet.getInt("idplanning");
                    String message = resultSet.getString("message");

                    ChatConversation conversation = new ChatConversation(id, id_chatSession, id_sender, id_reciever, idplanning,message);
                    conversations.add(conversation);
                }
            }
        }

        return conversations;
    }
    public void deleteConversationsByChatSessionId(int id_chatSession) throws SQLException {
        String sql = "DELETE FROM chat_conversation WHERE id_chatSession = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_chatSession);
            statement.executeUpdate();
        }
    }



    @Override
    public int getAdminIdByPlanningId(int planningId, int idcoach) {
        String sql = "SELECT id_sender FROM chat_conversation WHERE idplanning = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, planningId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_sender = resultSet.getInt("id_sender");
                    return  id_sender;
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace(); // Replace with proper error handling
        }

        // Return a default value or handle the case where no result is found
        return -1; // Or use any appropriate value
    }


}
