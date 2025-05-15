package at.samegger.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            BufferedReader input = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            String userInput;
            String response;
            String clientName = "empty";
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
            CLI cli = new CLI(input, output);
            cli.start();


        } catch (Exception e) {
            System.out.println("Exception in client main " + e);
        }
    }
}