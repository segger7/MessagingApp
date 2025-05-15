/*package at.samegger.dataaccess;

import at.samegger.SqlDatabaseConnection;
import at.samegger.domain.ChatUser;
import at.samegger.domain.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChatUserDAO implements DAOInterface<ChatUser>{

    private Connection con;

    public ChatUserDAO() throws SQLException, ClassNotFoundException {
        this.con = SqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/messagingapp","root","");
    }

    @Override
    public ChatUser findByID(Integer id) {
        try {
            String sql = "SELECT * FROM messages WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()) {
                Message message = new Message(
                        resultSet.getInt("id"),
                        resultSet.getString("text"),
                        chatDAO.findByID(resultSet.getInt("chat_id")),
                        userDAO.findByID(resultSet.getInt("sender_id")));
                return message;
            }


        } catch(SQLException sqlException) {
            System.out.println("Datenbankfehler: " + sqlException);
        }
        return null;
    }

    @Override
    public List<ChatUser> findAll() {
        return List.of();
    }

    @Override
    public ChatUser insert(ChatUser o) {
        return null;
    }

    @Override
    public void update(ChatUser o) {

    }

    @Override
    public void delete(Integer id) {

    }
}
*/