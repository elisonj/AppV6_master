package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import java.time.LocalDateTime;

/**
 * Created by elison on 11/07/17.
 */

public class Token  extends SugarRecord<Token> {

    private String accessToken;
    private long expiresIn;
    private LocalDateTime receivedAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}
