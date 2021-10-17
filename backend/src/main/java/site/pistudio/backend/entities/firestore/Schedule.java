package site.pistudio.backend.entities.firestore;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id
    private long id;

    private LocalDateTime time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}
