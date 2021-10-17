package site.pistudio.backend.entities.mysql;

import java.time.LocalDateTime;
import java.util.UUID;


public interface Role {


    public LocalDateTime getTokenExpired();

    public void setTokenExpired(LocalDateTime tokenExpired);

    public byte[] getTokenSecret();

    public void setTokenSecret(byte[] tokenSecret);

    public UUID getId();

    public void setId(UUID id);
}
