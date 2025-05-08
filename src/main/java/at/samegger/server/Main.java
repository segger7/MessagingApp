package at.samegger.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<>();
        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket, threadList);
            threadList.add(serverThread);
            serverThread.start();
        } catch(Exception e) {
            System.out.println("Error occurred in main of server: " + e.getStackTrace());

        }
    }
}
