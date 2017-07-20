package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by: elison
 * Date: 2017-07-12
 *
 * Represents a user's token. Used to authenticate with services.
 *
 * Warnings of "FieldCanBeLocal" are ignored if the field is not used because
 * the field needs to be present for Sugar to create it in the database
 */
class Token extends SugarRecord<Token> {

    @SuppressWarnings("FieldCanBeLocal")
    private String accessToken;

    @SuppressWarnings("FieldCanBeLocal")
    private long expiresIn;

    @SuppressWarnings("FieldCanBeLocal")
    private Date receivedAt;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public Token() {}

    Token(br.com.bg7.appvistoria.data.source.remote.dto.Token tokenFromService) {
        accessToken = tokenFromService.getAccessToken();
        expiresIn = tokenFromService.getExpiresIn();
    }
}
