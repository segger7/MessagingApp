package at.samegger.dataaccess;

import at.samegger.SqlDatabaseConnection;
import at.samegger.domain.Chat;
import at.samegger.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements DAOInterface<Chat>{

    private Connection con;

    public ChatDAO() throws SQLException, ClassNotFoundException {
        this.con = SqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/messagingapp","root","");
    }

    @Override
    public Chat findByID(Integer id) {
        try {
            String sql = "SELECT * FROM chats WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Chat chat = new Chat(
                    resultSet.getInt("id"),
                    resultSet.getString("chatname"),
                    resultSet.getBoolean("isGroup"));
            return chat;

        } catch(SQLException sqlException) {
            System.out.println("Datenbankfehler: " + sqlException);
        }
        return null;
    }

    @Override
    public List<Chat> findAll() {
        String sql = "SELECT * FROM chats";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            List<Chat> chats = new ArrayList<>();
            while(result.next()) {
                Chat chat = new Chat(
                        result.getInt("id"),
                        result.getString("chatname"),
                        result.getBoolean("isGroup"));
                chats.add(chat);
            }
            return chats;

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }
        return List.of();
    }

    @Override
    public Chat insert(Chat chat) {
        if(chat != null) {
            try{
                String sql = "INSERT INTO chats (chatname, isGroup) VALUES (?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, chat.getChatname());
                preparedStatement.setBoolean(2, chat.isGroup());

                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()) {
                    return this.findByID(generatedKeys.getInt(1));
                } else {
                    return chat;
                }

            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("Chat konnte nich hinzugefügt werden, null");
        }
        return chat;
    }

    @Override
    public void update(Chat chat) {
        if(chat != null) {
            try{
                String sql = "UPDATE chats chatname= ?, isGroup = ? WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, chat.getChatname());
                preparedStatement.setBoolean(2, chat.isGroup());
                preparedStatement.setInt(3, chat.getId());

                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("Chat konnte nich hinzugefügt werden, null");
        }
    }

    @Override
    public void delete(Integer id) {
        try{
            String sql = "DELETE FROM chats WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }
    }
}
