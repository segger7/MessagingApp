package at.samegger.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ClientMain {

    private static PrintWriter output;
    private static Scanner scanner;
    private static String userName;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            output = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);

            CountDownLatch loginLatch = new CountDownLatch(1);
            final boolean[] loginSuccess = {false};

            ClientThread clientThread = new ClientThread(socket,
                    response -> {
                        System.out.println(response.split("\\|")[1]); // Nachricht anzeigen
                        if (response.startsWith("LOGIN_SUCCESS|")) {
                            loginSuccess[0] = true;
                        }
                        loginLatch.countDown(); // Login-Versuch abgeschlossen
                    },
                    response -> {
                        System.out.println("Server: " + response); // z.B. Chat-Nachrichten
                    }
            );
            clientThread.start();

            // Login-Versuch
            System.out.println("Enter your Username:");
            String username = scanner.nextLine();
            System.out.println("Enter your Password:");
            String password = scanner.nextLine();
            userName = username;

            output.println("LOGIN|" + username + "|" + password);
            loginLatch.await(); // Warten auf Antwort

            if (!loginSuccess[0]) {
                System.out.println("Anmeldung fehlgeschlagen. Beende Client.");
                return;
            }

            // Ab hier bist du eingeloggt
            System.out.println("Du kannst jetzt Nachrichten senden. Gib 'exit' ein zum Beenden.");
            while (true) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    output.println("exit");
                    break;
                }
                output.println("MESSAGE|(" + userName + ") " + message);
            }

        } catch (Exception e) {
            System.out.println("Client-Fehler: " + e.getMessage());
        }
    }
}
