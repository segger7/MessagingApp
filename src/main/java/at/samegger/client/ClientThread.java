package at.samegger.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientThread extends Thread{

    private Socket socket;
    private BufferedReader input;
    private final Consumer<String> onLoginResponse;
    private final Consumer<String> onMessage;

    public ClientThread(Socket s, Consumer<String> onLoginResponse, Consumer<String> onMessage) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.onLoginResponse = onLoginResponse;
        this.onMessage = onMessage;
    }

    @Override
    public void run() {
        try {
            String response;
            while ((response = input.readLine()) != null) {
                if (response.startsWith("LOGIN_SUCCESS|") || response.startsWith("LOGIN_FAILED|")) {
                    onLoginResponse.accept(response);
                } else {
                    onMessage.accept(response);
                }
            }
        } catch (IOException e) {
            System.out.println("Verbindung zum Server verloren: " + e.getMessage());
        }
    }
}
