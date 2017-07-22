package br.com.bg7.appvistoria.data;

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

    public User clone(String token) {
        User user = new User();

        user.id = this.id;
        user.userName = this.userName;
        user.passwordHash = this.passwordHash;
        user.token = token;

        return user;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

}
