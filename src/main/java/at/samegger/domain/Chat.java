package at.samegger.domain;

public class Chat {

    private int id;

    private String chatname;

    private boolean isGroup;

    public Chat(int id, String chatname, boolean isGroup) {
        this.id = id;
        this.chatname = chatname;
        this.isGroup = isGroup;
    }

    public Chat(String chatname, boolean isGroup) {
        this.chatname = chatname;
        this.isGroup = isGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chatname='" + chatname + '\'' +
                ", isGroup=" + isGroup +
                '}';
    }
}
