package at.samegger.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

    public static void main(String[] args) {
        ServerThread.setThreadList(new ArrayList<>());
        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server gestartet...");
            while(true) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                ServerThread.threadList.add(serverThread);
                serverThread.start();
                System.out.println("Nutzer online...");
            }

        } catch(Exception e) {
            System.out.println("Error occurred in main of server: " + e.getStackTrace());

        }
    }
}
