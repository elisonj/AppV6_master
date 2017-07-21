package br.com.bg7.appvistoria.vo;

import com.orm.SugarRecord;

/**
 * Created by: elison
 * Date: 2017-07-12
 *
 * Represents a user in the app
 *
 * Warnings of "FieldCanBeLocal" are ignored if the field is not used because
 * the field needs to be present for Sugar to create it in the database
 */
public class User extends SugarRecord<User> {

    private String userName;

    private String passwordHash;

    private String token;

    /**
     * Default constructor used by Sugar
     */
    @SuppressWarnings("unused")
    public User() {}

    public User(String username, String token, String passwordHash) {
        this.userName = username;
        this.token = token;
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

}
