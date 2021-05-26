package persistence;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser ;
    private String username ;
    @OneToMany(targetEntity=MessageToSend.class, mappedBy="receiver")
    private List<MessageToSend> messagesToSend ;

    public User() {
    }

    public User(String username, List<MessageToSend> messagesToSend) {
        this.username = username;
        this.messagesToSend = messagesToSend;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MessageToSend> getMessagesToSend() {
        return messagesToSend;
    }

    public void setMessagesToSend(List<MessageToSend> messagesToSend) {
        this.messagesToSend = messagesToSend;
    }
}
