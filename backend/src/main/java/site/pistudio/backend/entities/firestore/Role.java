package site.pistudio.backend.entities.firestore;

import java.time.LocalDateTime;
import java.util.UUID;


public interface Role {


    public LocalDateTime getTokenExpired();

    public void setTokenExpired(LocalDateTime tokenExpired);

    public String getTokenSecret();

    public void setTokenSecret(String tokenSecret);

    public UUID getId();

    public void setId(UUID id);
}
