package site.pistudio.backend.entities.firestore;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;
import site.pistudio.backend.utils.MessageSender;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    private long id;

    private MessageSender messageSender;

    private LocalDateTime time;

    private String message;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
