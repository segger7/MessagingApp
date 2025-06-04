package at.samegger.ai;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class GeminiClient {
    private static final String API_KEY = ApiCredentials.GEMINI_API_KEY.getApiKey();
    private static final String ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-001:generateContent?key=" + API_KEY;

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        String prompt = "Tell me a short joke about computers.";

        // Build request body
        String requestBody = gson.toJson(Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        ));

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response);
                return;
            }

            // Parse JSON
            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray candidates = json.getAsJsonArray("candidates");

            if (candidates != null && candidates.size() > 0) {
                JsonObject candidate = candidates.get(0).getAsJsonObject();
                JsonObject content = candidate.getAsJsonObject("content");
                JsonArray parts = content.getAsJsonArray("parts");
                String text = parts.get(0).getAsJsonObject().get("text").getAsString();
                System.out.println("Gemini Response: " + text);
            } else {
                System.out.println("No response text found.");
            }
        }
    }

    public static String geminiQuestion(String prompt) {

        // Build request body
        String requestBody = gson.toJson(Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        ));

        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response);
                return "Failed";
            }

            // Parse JSON
            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray candidates = json.getAsJsonArray("candidates");

            if (candidates != null && candidates.size() > 0) {
                JsonObject candidate = candidates.get(0).getAsJsonObject();
                JsonObject content = candidate.getAsJsonObject("content");
                JsonArray parts = content.getAsJsonArray("parts");
                String text = parts.get(0).getAsJsonObject().get("text").getAsString();
                return "["+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "] [Gemini]:" + text;
            } else {
                return "No response text found.";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
