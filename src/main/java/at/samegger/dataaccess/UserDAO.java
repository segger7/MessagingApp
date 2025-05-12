package at.samegger.dataaccess;

import at.samegger.SqlDatabaseConnection;
import at.samegger.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAOInterface<User> {

    private Connection con;

    public UserDAO() throws SQLException, ClassNotFoundException {
        this.con = SqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/messagingapp","root","");
    }

    @Override
    public User findByID(Integer id) {
        try {
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            User user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"));
            return user;

        } catch(SQLException sqlException) {
            System.out.println("Datenbankfehler: " + sqlException);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while(result.next()) {
                User user = new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email")
                );
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }
        return List.of();
    }

    @Override
    public void save(User user) {
        if(user != null) {
            try{
                String sql = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getEmail());

                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("User konnte nich hinzugefügt werden, null");
        }

    }

    @Override
    public void update(User user) {
        if(user != null) {
            try{
                String sql = "UPDATE users name= ?, email = ? WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setInt(3, user.getId());

                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e);
            }
        } else {
            System.out.println("User konnte nich hinzugefügt werden, null");
        }
    }

    @Override
    public void delete(Integer id) {
        try{
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e);
        }


    }
}
