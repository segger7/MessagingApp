package at.samegger.dataaccess;

import at.samegger.SqlDatabaseConnection;
import at.samegger.domain.ChatUser;
import at.samegger.domain.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatUserDAO implements DAOInterface<ChatUser>{

    private Connection con;

    private UserDAO userDAO;
    private ChatDAO chatDAO;

    public ChatUserDAO() throws SQLException, ClassNotFoundException {
        this.con = SqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/messagingapp","root","");
        userDAO = new UserDAO();
        chatDAO = new ChatDAO();
    }

    @Override
    public ChatUser findByID(Integer id) {
        try {
            String sql = "SELECT * FROM chatuser WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()) {
                ChatUser chatuser = new ChatUser(
                        resultSet.getInt("id"),
                        userDAO.findByID(resultSet.getInt("user_id")),
                        chatDAO.findByID(resultSet.getInt("chat_id")));
                return chatuser;
            }


        } catch(SQLException sqlException) {
            System.out.println("Datenbankfehler: " + sqlException);
        }
        return null;
    }

    @Override
    public List<ChatUser> findAll() {
        String sql = "SELECT * FROM chatuser";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            List<ChatUser> chatusers = new ArrayList<>();


            while(result.next()) {
                ChatUser chatUser = new ChatUser(
                        result.getInt("id"),
                        userDAO.findByID(result.getInt("user_id")),
                        chatDAO.findByID(result.getInt("chat_id")));
                chatusers.add(chatUser);
            }
            return chatusers;

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }
        return List.of();
    }

    @Override
    public ChatUser insert(ChatUser chatuser) {
        if(chatuser != null) {
            try{
                String sql = "INSERT INTO chatuser (user_id, chat_id) VALUES (?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, chatuser.getUser().getId());
                preparedStatement.setInt(2, chatuser.getChat().getId());

                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()) {
                    return this.findByID(generatedKeys.getInt(1));
                } else {
                    return chatuser;
                }

            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("ChatUser konnte nich hinzugefügt werden, null");
        }
        return chatuser;
    }

    @Override
    public void update(ChatUser chatUser) {
        if(chatUser != null) {
            try{
                String sql = "UPDATE chatuser user_id = ?, chat_id = ? WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, chatUser.getUser().getId());
                preparedStatement.setInt(2, chatUser.getChat().getId());

                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("ChatUser konnte nich hinzugefügt werden, null");
        }

    }

    @Override
    public void delete(Integer id) {
        try{
            String sql = "DELETE FROM chatuser WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }
    }
}
