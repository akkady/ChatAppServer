package persistence;

import javax.persistence.*;

@Entity
public class MessageToSend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage ;

    @ManyToOne @JoinColumn(name="idUser", nullable=false)
    private User receiver ;
    private String sender;
    private String message ;

    public MessageToSend() {
    }

    public MessageToSend(User receiver, String sender, String message) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
    }

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
