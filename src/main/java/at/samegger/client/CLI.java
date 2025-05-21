package at.samegger.client;

import at.samegger.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI {

    private BufferedReader input;
    private PrintWriter output;
    private String userInput;
    private User activeUser;
    private Scanner scanner;
    private boolean isConnectedToUser;
    private String userName;

    public CLI(BufferedReader input, PrintWriter output) {
        this.input = input;
        this.output = output;
        scanner = new Scanner(System.in);
        isConnectedToUser = false;
        userInput = "";
    }

    public void start() throws IOException {

        while (!userInput.equals("exit")) {
            if (!isConnectedToUser) {
                System.out.println("Enter your Username");
                String username = scanner.nextLine();
                System.out.println("Enter your Password:");
                String password = scanner.nextLine();
                userInput = "LOGIN|" + username + "|" + password;
                output.println(userInput);
                if(input.readLine().equals("angemeldet!")) {
                    isConnectedToUser = true;
                    System.out.println("Erfolgreich angemeldet");
                }
            } else {
                String message = ("("+ userName +")" + " message: "); //Schauen warum es nicht hier hin springt DEBUGGER
                System.out.println(message);
                userInput = "MESSAGE|" + scanner.nextLine();
                output.println(message + " " + userInput);
            }
            if(userInput.equals("exit")) {
                break;
            }
        }

    }
}
