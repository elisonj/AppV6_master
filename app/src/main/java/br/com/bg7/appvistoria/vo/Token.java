package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import java.time.LocalDateTime;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class Token extends SugarRecord<Token> {

    private String accessToken;
    private long expiresIn;
    private LocalDateTime receivedAt;

    public Token(br.com.bg7.appvistoria.service.dto.Token tk) {
        accessToken = tk.getAccessToken();
        expiresIn = tk.getExpiresIn();
    }



    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public boolean isExpired() {
        //Todo:  implement
        return false;
    }

}
