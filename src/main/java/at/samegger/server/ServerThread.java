package at.samegger.server;

import at.samegger.dataaccess.ChatDAO;
import at.samegger.dataaccess.MessageDAO;
import at.samegger.dataaccess.UserDAO;
import at.samegger.domain.Message;
import at.samegger.domain.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServerThread extends Thread{

    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;
    private UserDAO userDAO;
    private MessageDAO messageDAO;
    private ChatDAO chatDAO;


    public ServerThread(Socket socket, ArrayList<ServerThread> threads) throws SQLException, ClassNotFoundException {
        this.socket = socket;
        this.threadList = threads;
        userDAO = new UserDAO();
        chatDAO = new ChatDAO();
        messageDAO = new MessageDAO();

    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while(true) {
                String incoming = input.readLine();
                if(incoming.equals("exit")) {
                    break;
                } else if(incoming == null) {
                    break;
                } else if(incoming.startsWith("LOGIN|")) {
                    String[] parts = incoming.split("\\|");
                    String username = parts[1];
                    String password = parts[2];
                    User user = userDAO.findByUserName(username);
                    if (user != null) {
                        if (user.getPassword().equals(password)) {
                            output.println("LOGIN_SUCCESS|Willkommen " + user.getName());
                            //this.loggedInUser = user;
                        } else {
                            output.println("LOGIN_FAILED|Falsches Passwort");
                        }
                    } else {
                        output.println("LOGIN_FAILED|Benutzer existiert nicht!");
                    }
                } else if(incoming.startsWith("MESSAGE|")) {
                    printToAllClients(incoming);
                    System.out.println("Server received " + incoming);

                    try{
                        String messageContent = incoming.substring("MESSAGE|".length());
                        String sender = messageContent.substring(messageContent.indexOf("(")+1,messageContent.indexOf(")"));
                        String message = messageContent.substring(messageContent.indexOf(")")+2);
                        messageDAO.insert(new Message(message, chatDAO.findByID(1), userDAO.findByUserName(sender), LocalDateTime.now() ));
                    } catch (Exception e) {
                        System.out.println("Message Datenbankfehler!: " + e.getStackTrace());
                    }


                }

            }
        } catch(Exception e) {
            System.out.println("Error occurred in main of server " + e.getStackTrace());

        }
    }
    private void printToAllClients(String output) {
        for(ServerThread sT : threadList) {
            sT.output.println(output);
        }
    }

}
