package at.samegger;

import at.samegger.dataaccess.UserDAO;
import at.samegger.domain.User;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try{
            UserDAO test = new UserDAO();
            User testuser = test.insert(new User("peter@test.at", "peter123"));
            System.out.println(testuser);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
