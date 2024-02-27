package entity;

public class ChatConversation {
    int id ;
    int id_chatSession;
    int id_sender;
    int id_reciever;
    int idplanning;
    String message;

    public ChatConversation(int id, int id_chatSession, int id_sender, int id_reciever, String message) {
        this.id = id;
        this.id_chatSession = id_chatSession;
        this.id_sender = id_sender;
        this.id_reciever = id_reciever;
        this.message = message;
    }

    public ChatConversation(int id, int id_chatSession, int id_sender, int id_reciever, int idplanning, String message) {
        this.id = id;
        this.id_chatSession = id_chatSession;
        this.id_sender = id_sender;
        this.id_reciever = id_reciever;
        this.idplanning = idplanning;
        this.message = message;
    }

    public int getIdplanning() {
        return idplanning;
    }

    public void setIdplanning(int idplanning) {
        this.idplanning = idplanning;
    }

    public void setId_sender(int id_sender) {
        this.id_sender = id_sender;
    }

    public void setId_reciever(int id_reciever) {
        this.id_reciever = id_reciever;
    }

    public int getId_sender() {
        return id_sender;
    }

    public int getId_reciever() {
        return id_reciever;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_chatSession(int id_chatSession) {
        this.id_chatSession = id_chatSession;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public int getId_chatSession() {
        return id_chatSession;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ChatConversation{" +
                "id=" + id +
                ", id_chatSession=" + id_chatSession +
                ", id_sender=" + id_sender +
                ", id_reciever=" + id_reciever +
                ", message='" + message + '\'' +
                '}';
    }
}
