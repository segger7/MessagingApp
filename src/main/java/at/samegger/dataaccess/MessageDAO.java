package at.samegger.dataaccess;

import at.samegger.SqlDatabaseConnection;
import at.samegger.domain.Chat;
import at.samegger.domain.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements DAOInterface<Message>{

    private Connection con;

    private UserDAO userDAO;
    private ChatDAO chatDAO;

    public MessageDAO() throws SQLException, ClassNotFoundException {
        this.con = SqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/messagingapp","root","");
        userDAO = new UserDAO();
        chatDAO = new ChatDAO();
    }

    @Override
    public Message findByID(Integer id) {
        try {
            String sql = "SELECT * FROM messages WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();


            resultSet.next();
            Message message = new Message(
                    resultSet.getInt("id"),
                    resultSet.getString("text"),
                    chatDAO.findByID(resultSet.getInt("chat_id")),
                    userDAO.findByID(resultSet.getInt("sender_id")));
            return message;

        } catch(SQLException sqlException) {
            System.out.println("Datenbankfehler: " + sqlException);
        }
        return null;
    }

    @Override
    public List findAll() {
        String sql = "SELECT * FROM messages";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            List<Message> messages = new ArrayList<>();


            while(result.next()) {
                Message message = new Message(
                        result.getInt("id"),
                        result.getString("text"),
                        chatDAO.findByID(result.getInt("chat_id")),
                        userDAO.findByID(result.getInt("sender_id")));
                messages.add(message);
            }
            return messages;

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }
        return List.of();
    }

    @Override
    public Message insert(Message message) {
        if(message != null) {
            try{
                String sql = "INSERT INTO messages (text, chat_id, sender_id) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, message.getText());
                preparedStatement.setInt(2, message.getChat().getId());
                preparedStatement.setInt(3, message.getSender().getId());

                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()) {
                    return this.findByID(generatedKeys.getInt(1));
                } else {
                    return message;
                }

            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("Message konnte nich hinzugefügt werden, null");
        }
        return message;
    }

    @Override
    public void update(Message message) {
        if(message != null) {
            try{
                String sql = "UPDATE messages text= ?, chat_id = ?, sender_id = ? WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, message.getText());
                preparedStatement.setInt(2, message.getChat().getId());
                preparedStatement.setInt(3, message.getSender().getId());

                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("Message konnte nich hinzugefügt werden, null");
        }
    }

    @Override
    public void delete(Integer id) {
        try{
            String sql = "DELETE FROM messages WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }
    }
}
