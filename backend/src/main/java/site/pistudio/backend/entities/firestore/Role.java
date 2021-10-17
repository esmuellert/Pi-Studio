package site.pistudio.backend.entities.firestore;

import java.time.LocalDateTime;


public interface Role {


    public LocalDateTime getTokenExpired();

    public void setTokenExpired(LocalDateTime tokenExpired);

    public byte[] getTokenSecret();

    public void setTokenSecret(byte[] tokenSecret);

    public String getId();

    public void setId(String id);
}
