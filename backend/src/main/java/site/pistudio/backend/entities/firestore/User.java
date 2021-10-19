package site.pistudio.backend.entities.firestore;

import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class User implements Role {
    @Id
    Key key;

    private UUID id;

    private String openId;

    private LocalDateTime registerDate;
    private LocalDateTime tokenExpired;
    private String tokenSecret;

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
    public String getTokenSecret() {
        return tokenSecret;
    }

    @Override
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
