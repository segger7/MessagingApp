package at.samegger.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {

    private int id;
    private String text;
    private Chat chat;
    private User sender;
    private LocalDateTime sentAt;

    public Message(int id, String text, Chat chat, User sender, LocalDateTime sentAt) {
        this.id = id;
        this.text = text;
        this.chat = chat;
        this.sender = sender;
        this.sentAt = sentAt;
    }

    public Message(String text, Chat chat, User sender, LocalDateTime sentAt) {
        this.text = text;
        this.chat = chat;
        this.sender = sender;
        this.sentAt = sentAt;

    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
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
