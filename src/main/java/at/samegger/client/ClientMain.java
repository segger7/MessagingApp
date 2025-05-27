package at.samegger.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                        if (response.startsWith("MESSAGE|")) { //In Chats unterteilen!
                            String messageContent = response.substring("MESSAGE|".length());
                            System.out.println(formatMessage(messageContent));
                        } else {
                            System.out.println(response);
                        }
                    },
                    response -> {
                        System.out.println(response.split("\\|")[1]);
                        if (response.startsWith("REGISTER_SUCCESS|")) {
                            loginSuccess[0] = true;
                        } else {
                            System.out.println("Server: " + response);
                        }
                        loginLatch.countDown();
                    }
            );
            clientThread.start();

            // Entweder registrieren oder anmelden (Login oder Register Dialog

            System.out.println("Wilkommen bei PingMe - Wollen sie sich anmelden (1) oder neu registrieren (2)?");
            String eingabe = scanner.nextLine();
            if(eingabe.equals("1")) {
                output.println(loginDialog());
            } else if(eingabe.equals("2")) {
                output.println(registerDialog());
            }

            loginLatch.await(); // Warten auf Antwort

            if (!loginSuccess[0]) {
                System.out.println("Anmeldung fehlgeschlagen. Beende Client.");
                return;
            }

            // Ab hier ist man eingeloggt



            while (true) {
                String chatAuswahl = chatAuswahlDialog();
                output.println(chatAuswahl);

                if(chatAuswahl.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println("Du kannst jetzt Nachrichten senden. Gib 'exit' ein zum Beenden.");
                while (true) {
                    String message = scanner.nextLine();
                    if (message.equalsIgnoreCase("exit")) {
                        output.println("exit");
                        break;
                    }
                    output.println("MESSAGE|(" + userName + ") " + message);
                }
            }


        } catch (Exception e) {
            System.out.println("Client-Fehler: " + e.getMessage());
        }
    }

    private static String formatMessage(String messageContent) {
        int start = messageContent.indexOf("(");
        int end = messageContent.indexOf(")");

        if (start != -1 && end != -1 && end > start) {
            String sender = messageContent.substring(start + 1, end);
            String message = messageContent.substring(end + 2);
            return "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "] " + "[" + sender + "]: " + message;
        } else {
            return "Unformatierte Nachricht: " + messageContent;
        }
    }

    private static String loginDialog() {
        System.out.println("Gib deinen Username ein:");
        String username = scanner.nextLine();
        System.out.println("Gib dein Password ein:");
        String password = scanner.nextLine();
        userName = username;

        return "LOGIN|" + username + "|" + password;
    }

    private static String registerDialog() {
        System.out.println("Erstelle einen neuen Username:");
        String username = scanner.nextLine();
        System.out.println("Erstelle ein neues Password:");
        String password = scanner.nextLine();
        System.out.println("Gib deine E-Mail Adresse ein:");
        String email = scanner.nextLine();

        userName = username;

        return "REGISTER|" + username + "|" + password + "|" + email;
    }

    private static String chatAuswahlDialog() {
        System.out.println("--- Deine Chats ---");
        output.println("CHATSREQUEST");

        String auswahl = scanner.nextLine();
        return "CHAT_PICKED|" + auswahl;
    }
}