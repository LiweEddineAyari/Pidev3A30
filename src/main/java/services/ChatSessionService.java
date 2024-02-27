package services;

import entity.chatSession;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatSessionService implements IChatSessionService<chatSession>{

    private Connection connection;

    public ChatSessionService() {
        connection= MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(chatSession chatSession) throws SQLException {


        String sql = "INSERT INTO chat_session (id_user, id_user2) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, chatSession.getId_user());
            statement.setInt(2, chatSession.getId_user2());
            statement.executeUpdate();
        }
    }
    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM chat_session WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public int getChatSession(chatSession chatSession) throws SQLException {
        String sql = "SELECT id\n" +
                "FROM chat_session\n" +
                "WHERE (id_user = ? AND id_user2 = ?)\n" +
                "   OR (id_user = ? AND id_user2 = ?);\n";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, chatSession.getId_user());
                statement.setInt(2, chatSession.getId_user2());
                statement.setInt(3, chatSession.getId_user2());
                statement.setInt(4, chatSession.getId_user());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return -1; // Or any other value indicating that the chat session was not found
                }
            }
        }
    }





}
