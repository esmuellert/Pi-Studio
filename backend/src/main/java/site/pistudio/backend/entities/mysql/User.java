package site.pistudio.backend.entities.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class User implements Role {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(unique = true)
    private String openId;

    private LocalDateTime registerDate;
    private LocalDateTime tokenExpired;
    private byte[] tokenSecret;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public LocalDateTime getTokenExpired() {
        return tokenExpired;
    }

    @Override
    public void setTokenExpired(LocalDateTime tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    @Override
    public byte[] getTokenSecret() {
        return tokenSecret;
    }

    @Override
    public void setTokenSecret(byte[] tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
