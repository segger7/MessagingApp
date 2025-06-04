package at.samegger.ai;

public enum ApiCredentials {
    GEMINI_API_KEY("AIzaSyBrNQb_ClhYY4pUqIFmrr8U2xQx1HATMqU");
    private final String apiKey;

    ApiCredentials(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}