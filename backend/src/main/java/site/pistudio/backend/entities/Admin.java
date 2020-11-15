package site.pistudio.backend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private LocalDateTime tokenExpired;
    private byte[] tokenSecret;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(LocalDateTime token_expired) {
        this.tokenExpired = token_expired;
    }

    public byte[] getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(byte[] token_secret) {
        this.tokenSecret = token_secret;
    }
}
