package at.samegger;

import at.samegger.dataaccess.ChatDAO;
import at.samegger.dataaccess.MessageDAO;
import at.samegger.dataaccess.UserDAO;
import at.samegger.domain.Chat;
import at.samegger.domain.Message;
import at.samegger.domain.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try{
            UserDAO test = new UserDAO();
            MessageDAO messageDAO = new MessageDAO();
            ChatDAO chatDAO = new ChatDAO();
            //User testuser = test.insert(new User("peter@test.at", "peter123"));
            //System.out.println(testuser);
            List<User> usersInDatenbank = test.findAll();

            Chat chat = new Chat("testchat", false);
            System.out.println(chat);
            chat = chatDAO.insert(chat);
            System.out.println(chat);

            Message message = new Message("Hallo das ist eine Testnachricht", chat, usersInDatenbank.getFirst(), LocalDateTime.now());
            System.out.println(message);
            message = messageDAO.insert(message);
            System.out.println(message);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
