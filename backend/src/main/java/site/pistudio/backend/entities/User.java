package site.pistudio.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class User {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(unique = true)
    private String openId;

    private LocalDateTime registerDate;
    private LocalDateTime tokenExpired;
    private byte[] tokenSecret;

    public UUID getId() {
        return id;
    }

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

    public LocalDateTime getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(LocalDateTime tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public byte[] getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(byte[] tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
