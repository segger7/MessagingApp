package at.samegger.domain;

public class Message {

    private int id;
    private String text;
    private Chat chat;
    private User sender;

    public Message(int id, String text, Chat chat, User sender) {
        this.id = id;
        this.text = text;
        this.chat = chat;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", chat=" + chat +
                ", sender=" + sender +
                '}';
    }
}
