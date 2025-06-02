package at.samegger.domain;

public class ChatUser {

    private int id;
    private User user;
    private Chat chat;

    public ChatUser(int id, User user, Chat chat) {
        this.id = id;
        this.user = user;
        this.chat = chat;
    }

    public ChatUser(User user, Chat chat) {
        this.user = user;
        this.chat = chat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
