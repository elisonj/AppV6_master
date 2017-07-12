package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by: elison
 * Date: 2017-07-12
 */
public class Token extends SugarRecord<Token> {

    private String accessToken;
    private long expiresIn;
    private Date receivedAt;

    public Token() {}

    public Token(br.com.bg7.appvistoria.service.dto.Token tokenFromService) {
        accessToken = tokenFromService.getAccessToken();
        expiresIn = tokenFromService.getExpiresIn();
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public boolean isExpired() {
        //Todo:  implement
        return false;
    }

}
